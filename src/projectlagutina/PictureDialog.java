package projectlagutina;

//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.event.ActionEvent;
//import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PictureDialog {

//    
//    public String getAnswer (String answer){
//          return (answer);
//    }
    public static void Picture(ImageView scView, Model ml, String answerFileName) throws IOException {
        //все растяжения сделать через проперти :)
        VBox root = new VBox(60);
        root.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setResizable(false);
//        String answer = null;

//        ArrayList <String> answers = new ArrayList <String> ();
        TextArea text = new TextArea();
        text.setPrefSize(300, 300);
        text.setEditable(true);
        root.getChildren().addAll(scView, text);
        
//        File oldFile = new File(answerFileName);
//        
//        if (oldFile.exists()){
//            
//            Scanner scan = new Scanner (oldFile);
//            StringBuilder builder = new StringBuilder();
//            while (scan.hasNextLine()){
//                builder.append(scan.nextLine());
//            }
//          System.out.println(builder.toString());
//            text.setText(builder.toString());
//            scan.close();
//        }    
    
            

//        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        stage.setOnCloseRequest((WindowEvent event1) -> {
            PrintWriter writer = null;
            try {
                File answerFile = new File(ml.getUpdatedAdress(), answerFileName);
                writer = new PrintWriter(answerFile);
                String answer = text.getText();
                writer.print(answer);
                stage.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PictureDialog.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }

        });

//          Button btnClose = new Button("Close");
//           btnClose.setOnAction((ActionEvent ae) -> {
//               writer.append(answer);
//                           stage.close();  
//               });
        stage.initModality(Modality.NONE);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);

        stage.setTitle("Picture");
        stage.showAndWait();

    }

}
