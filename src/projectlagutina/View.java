package projectlagutina;

//import java.io.BufferedWriter;
//import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
//import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.Pair;

public class View extends VBox {

    private final Model model;

    //LayOut'ы для отображения контента
    private HBox hbContent = new HBox();
    private VBox vbVideo;
    private VBox vbVocab;
    private TextArea fullTextOfLes;
    private TextArea vocablary;
    private Label subtitles = new Label();

    //LayOut для отображения меню
    private HBox hbBar;

    //Коллекция для субтитров по времени
    private ObservableMap<String, Duration> markers;
    private Pair<String, Duration> marker;

    //Отображение видео
    private MediaControl mc;
    private Media media;

//    private final ImageView back = new ImageView (new Image (View.class.getResourceAsStream("../data/background.jpg")));
    /**
     * Метод для создания пункта меню "Уроки"
     *
     * @return элемент меню, содержащий список уроков
     */
    public Menu createLesMenu() throws FileNotFoundException {
        Menu lesson = new Menu("Уроки");

        //Временная модель, нужна лишь для чтения названий уроков, 
        //используется до выбора урока
        Model modelTmp = new Model();

        //ДИНАМИЧЕСКОЕ наполнение уроками!!!
        for (int i = 0; i < modelTmp.getLessonQuantity(); i++) {
            MenuItem les = new MenuItem();

            //Установка названия элемента меню
            modelTmp.setLesNum(i + 1);
            try {
                les.setText(modelTmp.readTitle());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Установка Id для динамического составления пути к файлу
            les.setId((i + 1) + "");

            les.setOnAction((ActionEvent t) -> {
                hbContent.getChildren().clear();

                //Установка номера урока для наполнения модели
                model.setLesNum(Integer.parseInt(les.getId()));
                System.out.println(model.getLesNum() + " " + les.getId());

                //отражаем контент для выбранного урока
                try {
                    createPane();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            lesson.getItems().add(les);
        }

        return lesson;
    }

    /**
     * Метод для создания пункта меню "Субтитры"
     *
     * @return элемент меню, содержащий список возможных вариантов отображения
     * субтитров и текста
     */
    public Menu createSubMenu() {
        Menu menuSub = new Menu("Субтитры");

        MenuItem textRus = new MenuItem("Текст на русском");
        textRus.setOnAction(e -> {
            fullTextOfLes.clear();
            try {
                List<String> tmp = model.readTextRU();
                for (int i = 0; i < tmp.size(); i++) {

                    //appendText - это метод, который добавляет новую строку 
                    //в конец предыдущего текста
                    fullTextOfLes.appendText((String) tmp.get(i));
                    fullTextOfLes.appendText("\n");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
            fullTextOfLes.setVisible(true);
        });

        MenuItem subRus = new MenuItem("Субтитры на русском");
        subRus.setOnAction((ActionEvent ae) -> {
            subtitles.setVisible(true);
            subtitles.setText("RU");

            //Получение коллекции для привязки по времени
            markers = media.getMarkers();
            markers.clear();

            List<String> listSubRu = new ArrayList<String>();
            try {
                listSubRu = model.readSubRU();

                //Заполняем ObservableMap markers
                for (int i = 0; i < listSubRu.size(); i++) {
                    String timeStr = listSubRu.get(i + 1);
                    double time = Double.parseDouble(timeStr);
                    markers.put(listSubRu.get(i), Duration.seconds(time));
                    i++;
                }

                //Обновление видео плеера после добавления субтитров
                updateVideoBox();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem textFran = new MenuItem("Текст на французском");

        textFran.setOnAction(ae -> {
            fullTextOfLes.clear();
            try {
                List<String> tmp = model.readTextFR();
                for (int i = 0; i < tmp.size(); i++) {
                    fullTextOfLes.appendText((String) tmp.get(i));
                    fullTextOfLes.appendText("\n");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
            fullTextOfLes.setVisible(true);
        });

        MenuItem subFran = new MenuItem("Субтитры на французском");

        subFran.setOnAction(ae -> {
            subtitles.setVisible(true);
            subtitles.setText("FR");

            markers = media.getMarkers();
            markers.clear();

            List<String> listSubFr = new ArrayList<String>();
            try {
                listSubFr = model.readSubFR();

                //Заполняем ObservableMap markers
                for (int i = 0; i < listSubFr.size(); i++) {
                    String timeStr = listSubFr.get(i + 1);
                    double time = Double.parseDouble(timeStr);
                    markers.put(listSubFr.get(i), Duration.seconds(time));
                    i++;
                }

                //Обновление видео плеера после добавления субтитров
                updateVideoBox();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem closeSub = new MenuItem("Скрыть");
        closeSub.setOnAction(ae -> {
            subtitles.setVisible(false);
            fullTextOfLes.setVisible(false);
        });

        menuSub.getItems().addAll(textRus, subRus, textFran, subFran, closeSub);
        return menuSub;
    }

    /**
     * Метод для создания пункта меню "Задания"
     *
     * @return элемент меню, содержащий список заданий
     */
    public Menu createTaskMenu() {
        Menu menuTask = new Menu("Задания");
        MenuItem mt1 = new MenuItem("Задание 1");// называться будет по - другому
        mt1.setOnAction((ae) -> {
            try {
                Test1.Test(model);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem mt2 = new MenuItem("Задание 2");// называться будет по - другому
        mt2.setOnAction((ae) -> {
            try {
                Test2.Test(model);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem mt3 = new MenuItem("Задание 3");// называться будет по - другому
        mt3.setOnAction((ae) -> {
            try {
                Test3.Test(model);
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        menuTask.getItems().addAll(mt1, mt2, mt3);
        return menuTask;
    }

    /**
     * Метод для создания пункта меню "Ещё"
     *
     * @return элемент дополнительного меню
     */
    public Menu createExitMenu() { // доп меню
        Menu menuOther = new Menu("Еще");

        MenuItem me1 = new MenuItem("лалала");
        MenuItem me2 = new MenuItem("Закрыть приложение");
        menuOther.getItems().addAll(me1, me2);

        me2.setOnAction((ActionEvent t) -> {
            Platform.exit();
        });
        return menuOther;
    }

    /**
     * Метод для создания меню
     */
    private void createMenuBar() throws FileNotFoundException {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createLesMenu(), createSubMenu(),
                createTaskMenu(), createExitMenu());

        hbBar = new HBox(menuBar);
        hbBar.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Метод для обновления медиа контроллера
     *
     * Сохраняет прогресс видео перед удалением Удаляет видеоплеер из vbVideo и
     * обновляет его на основе нового media, а затем отображает его заново
     * Запускает видео с момента остановки
     */
    private void updateVideoBox() {
        //Сохраняем текущее время видео, на котором пользователь выбрал субтитры
        Duration savedTime = mc.getMediaPlayer().getCurrentTime();
        mc.getMediaPlayer().stop();

        //Удаление MediaController'а из контейнера
        vbVideo.getChildren().remove(mc);

        mc = new MediaControl(media);
        mc.getMediaPlayer().setOnMarker((MediaMarkerEvent e) -> {
            marker = e.getMarker();
            String markerText = marker.getKey();
            Duration markerTime = marker.getValue();
            subtitles.setText(markerText);
        });

        //Продолжаем видео с момента, на котором выбрали субтитры,
        //т.е. прервали видео
        mc.getMediaPlayer().setStartTime(savedTime);
        mc.getMediaPlayer().play();
        vbVideo.getChildren().add(0, mc);

    }

    /**
     * Метод для отображения контента (обновление контента при смене урока)
     *
     * @throws FileNotFoundException
     */
    private void createPane() throws FileNotFoundException {

        media = model.getMedia();
        mc = new MediaControl(media);
//        mc.setPrefSize(100, 50);

        hbContent.setPadding(new Insets(10, 10, 10, 10));

        //субтитры полным текстом
        fullTextOfLes = new TextArea();
        //fullTextOfLes.setPrefHeight(120);
        //fullTextOfLes.setPrefWidth(300);

        //запрещаем редактирование
        fullTextOfLes.setEditable(false);

        //текст разбивается на строки (убираем ScrollBar)
        fullTextOfLes.setWrapText(true);

        fullTextOfLes.setPrefWidth(800);

        vocablary = new TextArea();//словарик
        String tmp = null;

        //Создание словарика
        List<String> vocabList = model.readVocab();

        for (int i = 0; i < vocabList.size(); i++) {
            tmp = (String) vocabList.get(i);
            vocablary.appendText(tmp);
            vocablary.appendText("\n \n");
        }
        //vocablary.setMaxSize(200, 450);
        vocablary.setPrefHeight(486);
        vocablary.setEditable(false);
        vocablary.setWrapText(true);

        Label title = new Label(model.readTitle());

        vbVocab = new VBox(title, vocablary, subtitles);

        HBox fp = new HBox(20, fullTextOfLes);

        vbVideo = new VBox(mc, subtitles, fp);
        vbVideo.setPadding(new Insets(10.0));
        vbVideo.setSpacing(10.0);
        vbVideo.setPadding(new Insets(10, 10, 10, 10));

        hbContent.getChildren().addAll(/*back,*/vbVideo, vbVocab);

        title.getStyleClass().add("labelHead");
//        vbVideo.getStylesheets().add(Main.class.getResource("newCascadeStyleSheet.css").toExternalForm());
        vbVocab.getStylesheets().add(View.class.getResource("newCascadeStyleSheetView.css").toExternalForm());
//        vbVocab.getStylesheets().add(Main.class.getResource("newCascadeStyleSheet.css").toExternalForm());
    }

    public View(Model model) throws FileNotFoundException {
        this.model = model;
        createMenuBar();
        this.getChildren().addAll(hbBar, hbContent);
    }

}
