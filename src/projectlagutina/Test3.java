package projectlagutina;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Test3 {

    public static void Test(Model ml) throws UnsupportedEncodingException, IOException {
        ScrollPane root = new ScrollPane();
        HBox preRoot = new HBox();
//        File dir = new File("/parent/child").mkdir();
        File asw3 = new File(ml.getAdress() + "asw3.txt");
//        Alert alert = new Alert(AlertType.CONFIRMATION, "При повторном открытии задания вам придётся заполнять описания заново. Продолжить?", ButtonType.YES, ButtonType.NO);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        //VBox, ктр содержит HBox'ы с картинками
        VBox content = new VBox();

        List <Image> imagePicturesList = ml.readPicturesList();
        List <ImageView> imageViewPicturesList = new ArrayList<ImageView>();
        
        for (int i = 1; i <= 10; i++) {

//            FileInputStream f = new FileInputStream(ml.getAdress() + "/picture/sc" + i + ".png");
//            Image sc = new Image(f);
            ImageView scView = new ImageView(imagePicturesList.get(i-1));
//            scView.setFitHeight(200);
//            scView

            Tooltip t = new Tooltip("Нажмите на картинку и в открывшемся диалоговом окне введите описание картинки");
            Tooltip.install(scView, t);

//           
            ImageView copyScView = new ImageView(imagePicturesList.get(i-1));
            String fileName = new String("answer" + i + ".txt");
            scView.setOnMouseClicked((event) -> {

                try {
                    PictureDialog.Picture(copyScView, ml, fileName);
                } catch (IOException ex) {
                    Logger.getLogger(Test3.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            imageViewPicturesList.add(scView);

        }

        ArrayList<HBox> hBoxList = new ArrayList<HBox>();

        //переменная для цикла, в ктр HBox заполняется картинками      
        int j = 0;

        //переменная для добавления конкретного числа картинок в HBox
        int k = j + 2;

        //создание HBox и добавление в него картинок
        for (int i = 1; i <= 3; i++) {
            HBox hb = new HBox();
            for (j = j; j <= k; j++) {
                hb.getChildren().addAll(imageViewPicturesList.get(j));
            }
            k = j + 2;
            hBoxList.add(hb);
        }

        HBox hb = new HBox();
        hb.getChildren().addAll(imageViewPicturesList.get(9));
        hBoxList.add(hb);

        hBoxList.get(0).setAlignment(Pos.CENTER);
        hBoxList.get(1).setAlignment(Pos.CENTER);
        hBoxList.get(2).setAlignment(Pos.CENTER);
        hBoxList.get(3).setAlignment(Pos.CENTER);
        content.getChildren().addAll(hBoxList.get(0), hBoxList.get(1), hBoxList.get(2), hBoxList.get(3));
        root.setContent(content);
        root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        root.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

        preRoot.getChildren().addAll(root);
        preRoot.setAlignment(Pos.CENTER);
        Scene scene = new Scene(preRoot);

        
//        while (true){
                
        stage.setOnCloseRequest((WindowEvent event1) -> {
            
        
//            alert.setTitle("Information");
//            alert.setHeaderText(null);
//
//            alert.showAndWait();
            
//            if (alert.getResult() == ButtonType.YES) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(asw3);
                for (int i = 1; i <= imageViewPicturesList.size(); i++) {
                    try {
                        
//                        System.out.println(imageViewPicturesList.size() + "lalalala");
                        String pathToFileWithOldAnswer = new String(ml.getUpdatedAdress() + "answer" + i + ".txt");
                        File fileWithOldAnswer = new File(pathToFileWithOldAnswer);
                        Scanner scan = new Scanner(fileWithOldAnswer);
//                    FileReader reader = new FileReader(fileWithOldAnswer);
                        if (scan.hasNextLine()) {
                            String answer = new String(scan.nextLine());
                            System.out.println(answer);
                            writer.append(i + ") " + answer + "\r\n");
                        }
                        FileWriter oldWriter = new FileWriter(fileWithOldAnswer);
                        BufferedWriter bufferedWriter = new BufferedWriter(oldWriter);
                        bufferedWriter.write("");
                        bufferedWriter.close();
                    } catch (IOException ex) {
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Test3.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Test3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
//            }
            
//           if (alert.getResult() == ButtonType.NO){
//               alert.close();
//               
//           } 
            
            
        }); 
        

        stage.setScene(scene);
        stage.setHeight(600);
        stage.setTitle("Test 3");
        stage.setResizable(false);
        stage.showAndWait();

    }

}
