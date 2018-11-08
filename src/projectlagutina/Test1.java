package projectlagutina;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import static projectlagutina.Test1.Test;
//import static javafx.application.Application.launch;

public class Test1 {

    public static void Test(Model ml) throws FileNotFoundException, IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        
        
        File asw1 = new File(ml.getAdress() + "asw1.txt");
        FileWriter writer = new FileWriter(asw1); 

        ScrollPane preRoot = new ScrollPane();
        preRoot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        preRoot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setHgap(10);
        root.setVgap(10);

        preRoot.setContent(root);

        List<String> quest;
        quest = ml.readTask1();

     

//        String ex = quest.get(0);
//        System.out.println("1".codePointAt(0) + "________" + "0".codePointAt(0));
//        System.out.println(ex.codePointAt(0) + "________" + ex.codePointAt(1) + "________" + ex.codePointAt(2));
        
        int asw = Integer.parseInt(quest.get(0));//количество вопросов для Unicode
//        int asw = Integer.parseInt(quest.get(0).substring(1));//количество вопросов для UTF-8
        
        LinkedList <TextField> answers = new LinkedList<>();  
        int i = 0;
        String tmp = null;
        for (i = 1; i <= asw; i++) {//добавляем вопросы из файла + TextField для ответа
            tmp = quest.get(i);
            Label lab = new Label(i + ") " + tmp + " ");
            TextField ntf = new TextField(Integer.toString(i));
            TextField tf = new TextField();
            TextField etf = new TextField("\n");
            answers.add(ntf);
            answers.add(tf);
            answers.add(etf);
            root.add(lab, 0, i);
            root.add(tf, 1, i);
        }
        
         Button btnClose = new Button("Close");
           btnClose.setOnAction(ae -> {
                for(TextField n: answers) {
                  try {
                       writer.write (n.getText() + "\r\n");
                       System.out.println(n.getText());
                   } catch (IOException ex) {
                       Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               stage.close();
                   });
        
        root.add(btnClose, 0, i + 1);

        Scene scene = new Scene(preRoot);

        preRoot.getStylesheets().add(Test1.class.getResource("newCascadeStyleSheetTest1.css").toExternalForm());
//        scene.getStylesheets().add(View.class.getResource("newCascadeStyleSheetView.css").toExternalForm());
//        scene.getStylesheets().add(Main.class.getResource("newCascadeStyleSheetView.css").toExternalForm());

        stage.setScene(scene);
        stage.setHeight(600);
        stage.setTitle("Test 1");
        stage.showAndWait();
        
        writer.close();
    }
    
//    public void Print(LinkedList <TextField> answers) {
//        for(TextField n: answers) {
//                   try {
//                       writer.write (n.getText() + "\n");
//                       System.out.println(n.getText() + "\n");
//                   } catch (IOException ex) {
//                       Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
//                   }
//               }
//    }
    
    
}
