package dataset.setup;

/*-
 * #%L
 * tuna
 * %%
 * Copyright (C) 2018 Maxime Cordy, Matthieu Jimenez, University of Luxembourg and Namur
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import dataset.model.Software;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class gathering all softwares currently supported
 */
public class Softwares {

    public static List<Software> getAll() {
        List<Software> softwares = new ArrayList<>();
        softwares.add(getBcel());
        softwares.add(getBeansUtils());
        softwares.add(getCli());
        softwares.add(getCollections());
        softwares.add(getConfiguration());
        softwares.add(getCompress());
        softwares.add(getCSV());
        softwares.add(getDBUtils());
        softwares.add(getEMail());
        softwares.add(getFileUpload());
        softwares.add(getIo());
        softwares.add(getJCS());
        softwares.add(getJexl());
        softwares.add(getLang());
        softwares.add(getMath());
        softwares.add(getNet());
        softwares.add(getPool());
        softwares.add(getRng());
        softwares.add(getText());
        softwares.add(getVfs());
        return softwares;
    }

    public static Software getBcel() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("5.0", "4f2b59597e8463d85bad90b48c8d876815291f4f");
        versions.put("5.1", "0fa7513ef6a2ecdd7e14701fed33f8dcdaed0077");
        versions.put("5.2", "b546dfdaaaee9eec23c4d0bbe1454a2d6edcaca2");
        versions.put("6.0", "647c723ba1262e1ffce520524692b366a7fde45a");
        versions.put("6.1", "f70742fde892c2fac4f0862f8f0b00e121f7d16e");
        return new Software("Commons-BCEL", "BCEL", "https://github.com/apache/commons-bcel", versions);
    }

    public static Software getBeansUtils() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "41f492e7eaed0a9d3f32ee02ca8aa284472d526a");
        versions.put("1.1", "f4dd0a42d9d642dfc231c1290044fafd281c3fa3");
        versions.put("1.2", "a55d81e2b2b47d73c8dbf5e1de7f0394322290bd");
        versions.put("1.3", "227e5d2d4dd00ebbdf49004447e016431648d735");
        versions.put("1.4", "dc0f63ae7af19f7897e03d746038882c92d63c4a");
        versions.put("1.4.1", "4f1072a008e803bb59fd11fc108232f73b5c94ae");
        versions.put("1.5", "b72aa9e97b85714aad2951b9ff4e2be90a2940a7");
        versions.put("1.6", "2bc65c91d0c8ac5fac03c41625d10ddee6af7f27");
        versions.put("1.6.1", "3e7ecf18305b8cd8dd7f03e718f172a7502f82a8");
        versions.put("1.7.0", "cbdadd2011983734383a785466cdc16c06f99fe9");
        versions.put("1.8.0", "8fb2285efd72d72a359d9012216260a75bec09b7");
        versions.put("1.8.1", "7b6ba82ec6a50d3ff79e347c7544d69a7f154f20");
        versions.put("1.8.2", "74f39c4b65845364e6778a60c2498067dae98b0b");
        versions.put("1.8.3", "bcd28bd06687c9e7441c061572e5474e2151ff14");
        versions.put("1.9.0", "9c047b74f426e40b28f497c39572abcf48263650");
        versions.put("1.9.1", "8897e1b7497fe9a134b1a2cd0fc1dadc280710f7");
        versions.put("1.9.2", "0dbc77a2e036c5aa0ef48107f8e7b1e69bceab41");
        versions.put("1.9.3", "a83c1eae04c9c99360c3ba4e6a46fd89dc5407ef");
        return new Software("Commons-Beanutils", "BEANUTILS", "https://github.com/apache/commons-beanutils", versions);
    }

    public static Software getCli() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "086bdefe078bf6fa8bcdf29cc5fac496c25fd235");
        versions.put("1.1", "d95543de51dc1b3948fbffab0f890ad950c2d0e0");
        versions.put("1.2", "636fac44643c497373b747097bd46b457bddbc8e");
        versions.put("1.3", "d33c73f9d8cd8c6ae93f016596aad11351a8afce");
        versions.put("1.3.1", "41d3dbf00f3e2041f5e407b9b96d8f048ab388d9");
        versions.put("1.4", "f7153c3c10cc8060d3858a6a90d7ddccd0b3533e");
        return new Software("Commons-CLI", "CLI", "https://github.com/apache/commons-cli", versions);
    }

    public static Software getCollections() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "5e62ebe3c43f62ca5b58d9deac70154991ce0924");
        versions.put("2.0", "658dea9545701b813d9bebee1171c508392e472c");
        versions.put("2.1", "88186e85e517ff14f5f77b92f03a4f0b01939e36");
        versions.put("2.1.1", "e0687924eb6b13c02bf55169396840c276f7e8ae");
        versions.put("3.0", "ebdf7649e49d164d33c98cc6be4fbfacbbfc4220");
        versions.put("3.1", "326a1c172f5857709299bc77bd73402352214bbf");
        versions.put("3.2", "8792072a213366605161b2f6e315024711da7eee");
        versions.put("3.2.1", "0af3ae8f26388bcdec42d36bf66a1000a9221754");
        versions.put("3.2.2", "8e217b44a763565f395268be20e7cff1dedf747b");
        versions.put("3.3", "1d0b9abb31faf211192c9866e3ec5dfe86c41e1d");
        versions.put("4.0", "db189926f7415b9866e76cd8123e40c09c1cc67e");
        versions.put("4.1", "cb157163d7543f942a1391f3ef752ebea1e1b349");
        return new Software("Commons-Collections", "COLLECTIONS", "https://github.com/apache/commons-collections", versions);
    }

    public static Software getConfiguration() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "6c460662af216ac3777b03b9d641451486bc238d");
        versions.put("1.1", "3480ec5dd93416d3cd804a9d9463aba93e118af0");
        versions.put("1.2", "4c9cc64655258b6af313a593f981de4c94ccc28e");
        versions.put("1.3", "663be0ea1296075b919b41c7e3af4c10054c84aa");
        versions.put("1.4", "2ca32d357e00554c999837c52319e8122fcb26d9");
        versions.put("1.5", "6925324fa47ea463e8e7f7833dd2acc4c57ff170");
        versions.put("1.6", "46a1e1cf462d7f3cb89015287cb5cbe67a1ce22b");
        versions.put("1.7", "3bbf2a42b3db2878651a0968574ec375658f8590");
        versions.put("1.8", "fa910376536ea5a906d3a49a9d87603b11cf5133");
        versions.put("1.9", "93f43a4a6dcc7352888a4853e2178871c3c18523");
        versions.put("1.10", "3c212681e410097cf38ee376540da585a8938923");
        versions.put("2.0", "25c16206e0452a3b68abf4158c4d8f2ea38305da");
        versions.put("2.1", "e814fe00bb51256386dd78f3f926aa30d8de6a7c");
        versions.put("2.1.1", "31c1ab6bed9b9c7686111e00202f555de7471904");
        versions.put("2.2", "137997974e0ebd3acd047243f46ddf3b90fa0160");
        return new Software("Commons-Configuration", "CONFIGURATION", "https://github.com/apache/commons-configuration", versions);
    }

    public static Software getCompress() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "4b5962d0fc3082e456a7dd249fc25e5aa7b1d350");
        versions.put("1.1", "9ee24a3d6a84c7e09abb5a9b4940bc91e2ab23d6");
        versions.put("1.2", "dc733d1ec84c72d68d8cbedc8d4d301eaf975cbf");
        versions.put("1.3", "0874320927ef9427cf5da5973935a1d17201cdf7");
        versions.put("1.4", "6e63432a2a15c8b9b7327edb5cd141661890e0e9");
        versions.put("1.4.1", "30ea3746a0f3635c22b7a45f18c3617909f7595a");
        versions.put("1.5", "163c4eee17c041cb7c1ccfedd983816038cc1eca");
        versions.put("1.6", "c49e865f4eabd47e264063499e27e12eb549feb1");
        versions.put("1.7", "4a4ba6bfdadc792c59a67935b6c4fe6ee8bfd325");
        versions.put("1.8", "5048d93858a3a7d976ace35f83868215ce5dfe2c");
        versions.put("1.8.1", "3547741ac1f953e94b147ecec009a7460369c190");
        versions.put("1.9", "3a5c8403d892ff1bc245f5ccc6a00bbc860529eb");
        versions.put("1.10", "121b7e00017b95d1498c4f511ceb4dd21811257a");
        versions.put("1.11", "ff38bf57378c7cae8617b3c8df692a5ffbe7b83f");
        versions.put("1.12", "3623ee7dad07c74722baeeffcec6770015f915cf");
        versions.put("1.13", "45438471726fc5005a41cefecbf62d4d26eb15b2");
        versions.put("1.14", "dd7c7702bf51886aa8bd88b24d98619f310fbeda");
        versions.put("1.15", "01b06d5ef5c5ac3bd651bedcfec7433231cea371");
        return new Software("Commons-Compress", "COMPRESS", "https://github.com/apache/commons-compress", versions);
    }

    public static Software getCSV() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "4ef853bc13d3548ce9cae02c7c26ddbc1c790669");
        versions.put("1.1", "0d44e840563a3a26a7b47c39789a48d23cc21634");
        versions.put("1.2", "b493818433b788238fe80fa15d9c6427e52b8590");
        versions.put("1.3", "473e1b1eb9ceaa0fc956c0e76345fd1ab0575246");
        versions.put("1.4", "640b2f52dca971a977f146a32568ee00d33b45be");
        return new Software("Commons-CSV", "CSV", "https://github.com/apache/commons-csv", versions);
    }

    public static Software getDBUtils() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "de4cb838f7b55e07b36459552e1bebf154fe52cd");
        versions.put("1.1", "296ecfa5cba1bfabcbe945c30640230e69831f16");
        versions.put("1.2", "2cf8518621fbaece880eb9b450ece6a649b7266a");
        versions.put("1.3", "6978dd483cecee1414c391ee46205ec34d2539d5");
        versions.put("1.4", "9a74ab921d5f066d6d039639220b45b62d20ab1f");
        versions.put("1.5", "1919bb3ac5ed5b5d4e38e8f64e6b77aeb66c231c");
        versions.put("1.6", "f20ee57775a9f3365dae062055fb2248511be2a9");
        versions.put("1.7", "77faa3caefc24697b00bf370111f7307dae339b0");
        return new Software("Commons-DbUtils", "DBUTILS", "https://github.com/apache/commons-dbutils", versions);
    }

    public static Software getEMail() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "c53b324083f7138f2f2b3329a45ffa1f143d6abc");
        versions.put("1.1", "6a807655ea55ad5d891397ba305771294e88f42e");
        versions.put("1.2", "a2cf49af495e536516cf73d748b95618c1cc8b31");
        versions.put("1.3", "04c4e951d32cd61a59067c6e36948d383530124e");
        versions.put("1.3.1", "927b8943f396f8494b17e90608272fe2068c0df3");
        versions.put("1.3.2", "c05f496a699bda6189a8329cbcf6b942d8011b56");
        versions.put("1.3.3", "38233b1089a9c89211cea88eb9e144a061b0d60b");
        versions.put("1.4", "20ab7303a775342dc6ccfc8b0b7eb98b40738ec8");
        return new Software("Commons-Email", "EMAIL", "https://github.com/apache/commons-email", versions);
    }

    public static Software getFileUpload() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "cdfbeaa120cba6a8f1527b91600317ee374450c2");
        versions.put("1.1", "c876d8bb3583955acaca44a5e171b8ece3f507af");
        versions.put("1.1.1", "3dd42308fc227070c08b63b515de27018b184584");
        versions.put("1.2", "b738398b68b5ec8039adb96f1ea46c276efb9790");
        versions.put("1.2.1", "73b013c5f197a664024baba177cb33fbc1a9aa7d");
        versions.put("1.2.2", "5767b589c53412b794aa09a00c87399bc1e3140e");
        versions.put("1.3", "88e7fca87fcd194cbe88276be867aedab08b8e30");
        versions.put("1.3.1", "e19f0d04ff9d28e5e0d7bc6a4e98b6f04cec6bf8");
        versions.put("1.3.2", "1a01d4b321351ddc8b0cabafe09aa1e96ae7f08d");
        versions.put("1.3.3", "18734e9f77a267ebc82ff2ffce6d96e82a34260f");
        return new Software("Commons-FileUpload", "FILEUPLOAD", "https://github.com/apache/commons-fileupload", versions);
    }

    public static Software getIo() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "c2970b4a557e45a1f6e3cf85b5d163a5de9f80dd");
        versions.put("1.1", "8d911c48d70c93190c8007d915162040a05d89fe");
        versions.put("1.2", "d65ff7fec954c84deb0c3b22731b0ee8f395805e");
        versions.put("1.3", "5a6f8945b08bcb6a4c47cc148a8cbb2f42271880");
        versions.put("1.3.1", "51aa1d250035dd89db576cd8458b26c58d78dea8");
        versions.put("1.3.2", "04dda36003d8d5115a4210e228deb52deba2f2c6");
        versions.put("1.4", "6923a01ae1edf0510dd257f72867c282224ee23f");
        versions.put("2.0", "27add961ae6f94fa20124d24486a6e5fb47587c9");
        versions.put("2.0.1", "d5ac035b851c687b11031e694c40318db09bc634");
        versions.put("2.1", "0e68a33a9b8f27e592f79a563a5c9a9644c8b21d");
        versions.put("2.2", "5d4d09928e154d083f396a1555da72dfe9d877d1");
        versions.put("2.3", "c07589baa429c2ab306466643cef3dec31d1788b");
        versions.put("2.4", "e36d53170875d26d59ca94bd376bf40bc5690ee6");
        versions.put("2.5", "4077158829de92987367d3149e4ba71356bb5390");
        return new Software("Commons-IO", "IO", "https://github.com/apache/commons-io", versions);
    }

    public static Software getJCS() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("2.0", "8a3cab728832eb739a9697d3a5d2e0184d511511");
        versions.put("2.1", "efe68fda0c1c4ef57171e76ef10ca118b96e191a");
        versions.put("2.2", "5a9246493fb9c60eb81ba4eab7636172e438d46c");
        versions.put("2.2.1", "6ed9dff175110b835bffcfc35cf5ea07a5193304");
        versions.put("1.0", "e2349a328c0d17ac6add6e28ea56a16cb640f27a");
        versions.put("1.3", "851ec7399ea430c9be9ec4c8914d839efdd92b10");
        return new Software("Commons-JCS", "JCS", "https://github.com/apache/commons-jcs", versions);
    }

    public static Software getJexl() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "d4e5fb3c1e1d09641e7d7fc5418cde5e863a56e8");
        versions.put("1.1", "b9feb29d0d3abbe0d441685f04014036b71e9106");
        versions.put("2.0", "1abdc102c66da73706b2aef331cb57e08f595c04");
        versions.put("2.0.1", "b969a32a0e4645e3b11f07019e96f8c27d755b42");
        versions.put("2.1", "a863c774bc1e95ccc7d35e6b1ff56f8a34a7698c");
        versions.put("2.1.1", "6ce6fc20aa23635fa534af90eb960a6fc92baf15");
        versions.put("3.0", "de6c4f3b00af4430f535fcb7833c480d9093fd35");
        versions.put("3.1", "af298b37d30f8cd1e776b23db9bb85c353b002ee");
        return new Software("Commons-JEXL", "JEXL", "https://github.com/apache/commons-jexl", versions);
    }

    public static Software getLang() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "2d274d7cedc148ee3fa8cf4f48904dfd82172bda");
        versions.put("1.0.1", "e1e9d4d7c84809fea2f91d600d29f4a65cffb1d4");
        versions.put("2.0", "da5d64110a8b7182d04359958f40fadfbe1ed6ff");
        versions.put("2.1", "81f1077a922523a1f6a76376215a1b3de1e49b67");
        versions.put("2.2", "bdf2ede50ed073d91389be55ec3a9cff565c58a5");
        versions.put("2.3", "4e55808486a0227a72c70350a76d33660cc94af8");
        versions.put("2.4", "aa9727e15966bac899aa18733ef85c4aded19f0c");
        versions.put("2.5", "7c4386cb877d99d733b531f4fba2a328a3951767");
        versions.put("2.6", "0fe6a434f20b45c027badd89bd393dd4085cf1f2");
        versions.put("3.0", "c21484b730221bc87ca26553155350292aa30f0d");
        versions.put("3.0.1", "b901f0b449bc01358ac5b9e7fb8c82910a0b22e7");
        versions.put("3.1", "915946c9dcc80a6a16745b86540b82b2ddba1720");
        versions.put("3.2", "0c14a39be2b8a8e9461921994033310fd567e8cb");
        versions.put("3.2.1", "92e7e569ca9a6c92f24cc88c05e182d18ad62847");
        versions.put("3.3", "37f8fe84d934a92fe784c62e706b7aacf6fa4f97");
        versions.put("3.3.1", "27676c620a7dbb3365311c3424554c603bb11470");
        versions.put("3.3.2", "e8f924f51be5bc8bcd583ea96e5ef25f9b2ca72a");
        versions.put("3.4", "4777c3a5e4291af2420db57d008152c70c4a8f24");
        versions.put("3.5", "36f98d87b24c2f542b02abbf6ec1ee742f1b158b");
        versions.put("3.6", "09043bfa6f1f9ebb946c7b159735c83259e3a89f");
        return new Software("Commons-Lang", "LANG", "https://github.com/apache/commons-lang", versions);
    }

    public static Software getMath() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "96691abe5ed4a0f67b253cd2f6f789e868a77b22");
        versions.put("1.1", "bb50284d69ce651dc12e7acea2fe6e2db792874a");
        versions.put("1.2", "0c602508d1591e212c55912a9066baa45e126957");
        versions.put("2.0", "9f26e58bf9a73e1375b17dfdc2c5f395e4ffbdb6");
        versions.put("2.1", "7d588288e35b38dd55e7bedb767d1b0d80096294");
        versions.put("2.2", "98ebcc6bb298e62e35f895d59fca51b8c4c2d443");
        versions.put("3.0", "174f17d5d4b8251c4ed2600947e69b736ace5877");
        versions.put("3.1", "b090abb2cf03cf4d0afc6fbadc7cff62c35077cd");
        versions.put("3.1.1", "6c63f441a7da0d22f69ba557721474dc9b9b1ba5");
        versions.put("3.2", "7ec508828f62c2a3df31cde2a2f3aa32c5135fdb");
        versions.put("3.3", "38a48e20867f4cd91b7540be4bbba1e9dc7a8b02");
        versions.put("3.4", "befd8ebd96b8ef5a06b59dccb22bd55064e31c34");
        versions.put("3.4.1", "ef6e0f882819e7c5230aece1610297e67775cca2");
        versions.put("3.5", "b3c5dae8f253fcb4484e5cd3cc5662587803efc2");
        versions.put("3.6", "95a9d35e77f70ffc9bd5143880c236a760b42005");
        versions.put("3.6.1", "16abfe5de688cc52fb0396e0609cb33044b15653");
        return new Software("Commons-Math", "MATH", "https://github.com/apache/commons-math", versions);
    }

    public static Software getNet() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "edb0c4426ab7dc930c29a5eafd3f7ac76c4cd9bf");
        versions.put("1.1.0", "ce903621374d97ab641ca6e0f07d236236a50970");
        versions.put("1.1.1", "44f9c6cd1a793d62f7dca1cb2f27a84846fbc8f4");
        versions.put("1.2", "505a39a18df1507ad63af7a636eaa25b3b793721");
        versions.put("1.2.1", "6684e2d66e510b1100848ab0fbb89206d32c2650");
        versions.put("1.2.2", "9c2bb5739117cef5dd749c3068d2bd342937b70a");
        versions.put("1.3", "4f8f123cda3bebdf22095e80826cf4af5781ceb9");
        versions.put("1.4", "380f6f7a3bca6819b88656daa4c37f770958d2ac");
        versions.put("1.5", "fbbdb3ab69ca3333b985c6222742cefe2f4894a1");
        versions.put("2.0", "32c972d4a86302c8df0be318d9380e8989ea6972");
        versions.put("2.1", "6fe0b0c317a3ac250abedc700dd1d0483450604f");
        versions.put("2.2", "51829d33e280cd335b0d48db9e290a82ae574496");
        versions.put("3.0", "7b6def4cb8da5ddef57efdce7f415f7aee1b49e7");
        versions.put("3.0.1", "70a1ff4261f239feb2cecc207cdb1e7fa656ecfa");
        versions.put("3.1", "e2fa6168cdbe958e56d54456e7aa1ecc4b13e4df");
        versions.put("3.2", "9f139947e62438f408eb45fcbf8543eaf0d2b922");
        versions.put("3.3", "f8c2dabb0e8cb62c86912dc61fe2f68106d8b917");
        versions.put("3.4", "74a2282b7e4c6905581f4f1b5a2ec412310cd5e7");
        versions.put("3.5", "943a587727079179b1c42af26b788f1cb5d76643");
        versions.put("3.6", "163fe46c019f5184016207c247cdff30ee740ecc");
        return new Software("Commons-Net", "NET", "https://github.com/apache/commons-net", versions);
    }

    public static Software getPool() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "af75ff68fe995a6855eb806af590483822f5ba9a");
        versions.put("1.0.1", "8351007afa3ac79bfb511698fee3cc51c0bd81c9");
        versions.put("1.1", "8f19d70159e2b1658b81982ef3a896b19946be97");
        versions.put("1.2", "e3035f842022af05d6bfd6c4645c555bde89f073");
        versions.put("1.3", "222aab3a9adcc83278935039d6275c88cc8984df");
        versions.put("1.4", "343dc92b4a3b03159a8b8ba14a6cf357b0be44ae");
        versions.put("1.5", "64989df7d62e969881e198b9171abf1b77b1bf81");
        versions.put("1.5.1", "6fefeb0a314d966d412b2f7f4846dba383a3a23e");
        versions.put("1.5.2", "93bfcc0050d3be56a3beb1b3a27c3b285d0bbbff");
        versions.put("1.5.3", "4f0c069aa446ce1115fb164d1ba7989e5a7e9bb8");
        versions.put("1.5.4", "be2f91441632623f84c48dac2afe468e5a14b4a4");
        versions.put("1.5.5", "4bd78f90803d835d1ab9cdbfa73a03bc1d743a58");
        versions.put("1.5.6", "956104701f48b2bd080c022ca7e0352670e750fd");
        versions.put("1.5.7", "1c486e415cbb522e37f4c49697e91513a51a20e5");
        versions.put("1.6", "0e348d9c5e4649543f1975dc41a39d484c1607c5");
        versions.put("2.0", "b0e4868e40511e7aad229152abd6a1c15def6218");
        versions.put("2.1", "a28d908dd2087d47137953952dbae83c0f48e3b7");
        versions.put("2.2", "f50e3a0e0cf7fddcce8154db5799437ed9a8408a");
        versions.put("2.3", "7ecfff80b3d7e43a165556e67e1579f2af0dc7d2");
        versions.put("2.4", "1327fccdf2dc551eb856540031413f7f8beb9119");
        versions.put("2.4.1", "85fed3ab8ce0f4a3ae7ec140b508c787eca945b6");
        versions.put("2.4.2", "a187fd494b1d7f486edccb3356a70dd7846445a0");
        return new Software("Commons-Pool", "POOL", "https://github.com/apache/commons-pool", versions);
    }

    public static Software getRng() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "4581a4520315657a4219b37c81f5db80e4a4e43c");
        return new Software("Commons-RNG", "RNG", "https://github.com/apache/commons-rng", versions);
    }

    public static Software getText() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "e38039a3da2244741f5d33ab1b05bdee51c53c3e");
        versions.put("1.1", "206931af2ad7084044aba19591a5034b634c23b1");
        return new Software("Commons-Text", "TEXT", "https://github.com/apache/commons-text", versions);
    }

    public static Software getVfs() {
        HashMap<String, String> versions = new HashMap<>();
        versions.put("1.0", "ff7dd9697fac9856c8acae2ab06031f83f70e33c");
        versions.put("2.0", "5ba9d07161453072d2e12c4ff2f04f0f3168d057");
        versions.put("2.1", "f159bd4781b5ea91ae8872c2cd729d12511703e9");
        versions.put("2.2", "8cabebbbb9f6012808634f63f4deba77f81cb5c2");
        return new Software("Commons-VFS", "VFS", "https://github.com/apache/commons-vfs", versions);
    }

}
