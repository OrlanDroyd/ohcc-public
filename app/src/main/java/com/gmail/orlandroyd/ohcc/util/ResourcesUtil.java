package com.gmail.orlandroyd.ohcc.util;

import com.gmail.orlandroyd.ohcc.R;

import java.util.HashMap;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public abstract class ResourcesUtil {

    /**
     * PDF-LIBRARY
     */
    private static final String
            Doc1 = "Libro de Regulaciones",
            Doc2 = "Gaceta Oficial No. 23\nExtraordinaria 17 junio 2015",
            Doc3 = "Gaceta Oficial No. 35\nExtraordinaria 10 julio 2018",
            Doc4 = "Gaceta Oficial No. 77\nExtraordinaria 5 diciembre 2018",
            Doc5 = "Documentación para TCP",
            Doc6 = "Reglamento de la Plaza San Juan de Dios",
            Doc7 = "Reglamento Parque Agramonte",
            Doc8 = "Reglamento Casino Campestre",
            Doc9 = "Reglamento Centro Servicios de Ciudad",
            Doc10 = "Reglamento Centro Servicios de Ciudad\nAnexo 1",
            Doc11 = "Reglamento Centro Servicios de Ciudad\nAnexo 2",
            Doc12 = "Reglamento Centro Servicios de Ciudad\nAnexo 3",
            Doc13 = "Ley No.1",
            Doc14 = "Ley No.2",
            Doc15 = "Decreto No.55",
            Doc16 = "Decreto No.118",
            Doc17 = "Edificios 2",
            Doc18 = "Inmuebles GP2";

    private static final String
            asset1 = "libro_de_regulaciones_camaguey.pdf",
            asset2 = "goc_ex23.pdf",
            asset3 = "goc_ex35.pdf",
            asset4 = "goc_ex77.pdf",
            asset5 = "doc_tcp.pdf",
            asset6 = "plaza_san_juan_de_dios.pdf",
            asset7 = "parque_agramonte.pdf",
            asset8 = "casino.pdf",
            asset9 = "reglamento.pdf",
            asset10 = "reglamento_anexo1.pdf",
            asset11 = "reglamento_anexo2.pdf",
            asset12 = "reglamento_anexo3.pdf",
            asset13 = "ley1.pdf",
            asset14 = "ley2.pdf",
            asset15 = "decreto55.pdf",
            asset16 = "decreto118.pdf",
            asset17 = "edificios2.pdf",
            asset18 = "inmuebles_gp2.pdf";

    public static String[] titlesDocuments = {Doc1, Doc2, Doc3, Doc4, Doc5, Doc6, Doc7, Doc8, Doc9, Doc10, Doc11, Doc12, Doc13, Doc14, Doc15, Doc16, Doc17, Doc18};
    public static int[] thumnailDocuments = {R.drawable.ic_library_books_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp, R.drawable.ic_assignment_24dp};

    public static String getAssetByTitle(String title) {
        HashMap<String, String> raw = loadRaw();
        return raw.get(title);
    }

    private static HashMap<String, String> loadRaw() {
        HashMap<String, String> raw = new HashMap<>();
        raw.put(Doc1, asset1);
        raw.put(Doc2, asset2);
        raw.put(Doc3, asset3);
        raw.put(Doc4, asset4);
        raw.put(Doc5, asset5);
        raw.put(Doc6, asset6);
        raw.put(Doc7, asset7);
        raw.put(Doc8, asset8);
        raw.put(Doc9, asset9);
        raw.put(Doc10, asset10);
        raw.put(Doc11, asset11);
        raw.put(Doc12, asset12);
        raw.put(Doc13, asset13);
        raw.put(Doc14, asset14);
        raw.put(Doc15, asset15);
        raw.put(Doc16, asset16);
        raw.put(Doc17, asset17);
        raw.put(Doc18, asset18);
        return raw;
    }

    /**
     * PDF-INDEX
     */
    private static final String
            indexDoc1 = "Derroteros",
            indexDoc2 = "Patrimonio Mundial",
            indexDoc3 = "Estructura y trazado urbano",
            indexDoc4 = "Categorías de intervención por grados de protección de los edificios",
            indexDoc5 = "Elementos regulados por grado de protección de los edificios",
            indexDoc6 = "Grados de protección de los edificios",
            indexDoc7 = "Espacios públicos y áreas verdes",
            indexDoc8 = "Componentes del paisaje urbano",
            indexDoc9 = "Áreas de inundación",
            indexDoc10 = "Tablas y normas gráficas",
            indexDoc11 = "Glosario";

    private static int[]
            arrayIndex1 = {179, 180, 181, 182, 183, 184, 185},
            arrayIndex2 = {186, 187, 188},
            arrayIndex3 = {197},
            arrayIndex4 = {215},
            arrayIndex5 = {216},
            arrayIndex6 = {217},
            arrayIndex7 = {224},
            arrayIndex8 = {230},
            arrayIndex9 = {235},
            arrayIndex10 = {236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263},
            arrayIndex11 = {266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276};

    private static final String
            indexAsset1 = "libro_de_regulaciones_camaguey.pdf";

    public static final String[] titlesDocumentsIndex = {indexDoc1, indexDoc2, indexDoc3, indexDoc4, indexDoc5, indexDoc6, indexDoc7, indexDoc8, indexDoc9, indexDoc10, indexDoc11};

    public static String getAssetByTitleIndex(String title) {
        HashMap<String, String> raw = loadRawIndex();
        return raw.get(title);
    }

    private static HashMap<String, String> loadRawIndex() {
        HashMap<String, String> raw = new HashMap<>();
        raw.put(indexDoc1, indexAsset1);
        raw.put(indexDoc2, indexAsset1);
        raw.put(indexDoc3, indexAsset1);
        raw.put(indexDoc4, indexAsset1);
        raw.put(indexDoc5, indexAsset1);
        raw.put(indexDoc6, indexAsset1);
        raw.put(indexDoc7, indexAsset1);
        raw.put(indexDoc8, indexAsset1);
        raw.put(indexDoc9, indexAsset1);
        raw.put(indexDoc10, indexAsset1);
        raw.put(indexDoc11, indexAsset1);
        return raw;
    }

    public static int[] getPagesByTitle(String title) {
        switch (title) {
            case indexDoc1:
                return arrayIndex1;
            case indexDoc2:
                return arrayIndex2;
            case indexDoc3:
                return arrayIndex3;
            case indexDoc4:
                return arrayIndex4;
            case indexDoc5:
                return arrayIndex5;
            case indexDoc6:
                return arrayIndex6;
            case indexDoc7:
                return arrayIndex7;
            case indexDoc8:
                return arrayIndex8;
            case indexDoc9:
                return arrayIndex9;
            case indexDoc10:
                return arrayIndex10;
            case indexDoc11:
                return arrayIndex11;
            default:
                return null;
        }
    }
}
