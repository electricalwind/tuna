package modelling.infrastructure.kylm;

import modelling.util.SymbolSet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassMap implements Serializable {

    private static final long serialVersionUID = 1707818585575921117L;

    public SymbolSet classes = null;
    public List<Integer> idMap = null;
    public List<Float> probMap = null;

    private static boolean eq(Object o1, Object o2) {
        if (o1 == null) return o2 == null;
        return o1.equals(o2);
    }

    public boolean equals(Object o) {
        ClassMap cm = (ClassMap) o;
        ((ArrayList) cm.idMap).trimToSize();
        ((ArrayList) cm.probMap).trimToSize();
        return eq(classes, cm.classes) &&
                eq(idMap, cm.idMap) &&
                eq(probMap, cm.probMap);
    }

    public ClassMap() {
        classes = new SymbolSet();
        idMap = new ArrayList<>(); //new int[size];
        probMap = new ArrayList<>();
    }

    public ClassMap(int size) {
        classes = new SymbolSet();
        idMap = new ArrayList<Integer>(size);
        Collections.fill(idMap, -1);//new int[size];
        probMap = new ArrayList<Float>(size);
    }

    public void addEntry(int vid, int cid, float prob) {
        while (vid >= idMap.size()) {
            idMap.add(-1);
            probMap.add(0.0f);
        }
        idMap.set(vid, cid);
        probMap.set(vid, prob);
    }

    public int addClass(String symbol) {
        return classes.addSymbol(symbol);
    }

    public int getWordSize() {
        return idMap.size();
    }

    public int getWordClass(int i) {
        return idMap.get(i);
    }

    public float getWordProb(int i) {
        return probMap.get(i);
    }

    public String getClassSymbol(int wordClass) {
        return classes.getSymbol(wordClass);
    }

    public int getClassSize() {
        return classes.getSize();
    }

    public void setWordProb(int i, float prob) {
        probMap.set(i, prob);
    }

    ///////////////////////////////
    // methods for serialization //
    ///////////////////////////////
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(classes);
        out.writeObject(idMap);
        out.writeObject(probMap);
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        try {
            classes = (SymbolSet) in.readObject();
        } catch (NullPointerException e) {
        }
        try {
            idMap = (ArrayList<Integer>) in.readObject();
        } catch (NullPointerException e) {
        }
        try {
            probMap = (ArrayList<Float>) in.readObject();
        } catch (NullPointerException e) {
        }
    }

    public SymbolSet getClasses() {
        return classes;
    }


}
