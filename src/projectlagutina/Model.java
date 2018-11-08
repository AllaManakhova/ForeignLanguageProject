package projectlagutina;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Model {

    //Номер урока
    private int lesNum;

    //Французские и русские субтитры
//    private String subFR;
//    private String subRus;

    //Словарик
//    private String vocab;

    //Адресная строка
    private String adress = "data/L1/Video.mp4";

//    Model(/*String subFR, String subRus,*/ String task, /*String vocab*/) {
////        this.subFR = subFR;
////        this.subRus = subRus;
////        this.task = task;
////        this.vocab = vocab;
//    }

    public Model() {
    }

//    public String getVocab() {
//        return vocab;
//    }
//
//    public void setVocab(String vocab) {
//        this.vocab = vocab;
//    }

//    public String getSubFR() {
//        return subFR;
//    }
//
//    public String getSubRus() {
//        return subRus;
//    }

    public int getLesNum() {
        return lesNum;
    }

    public void setLesNum(int lesNum) {
        this.lesNum = lesNum;
        updateAdress();
    }

    public void updateAdress() {
        this.adress = "data/L" + lesNum + "/";
    }
    
    public String getUpdatedAdress() {
        this.adress = "data/L" + lesNum + "/tmp/";
        return adress;
    }
    
    public String changeAdress() {
        this.adress = "data/L" + (lesNum + 1) + "/";
        System.out.println(adress);
        return adress;
    }
    
    public String getAdress() {
        return adress;
    }

//    public void setSubFR(String subFR) {
//        this.subFR = subFR;
//    }
//
//    public void setSubRus(String subRus) {
//        this.subRus = subRus;
//    }

    /**
     * Метод для считывания количества уроков
     * 
     * @return число, означающее количество уроков
     * @throws FileNotFoundException
     */
    public int getLessonQuantity() throws FileNotFoundException {//считываем заголовок 
        this.adress = "data/lessonQuantity.txt";
        File quantity = new File(adress);
        FileReader reader_quantity = new FileReader(quantity);
        Scanner quantity_sc = new Scanner(reader_quantity);
        int quantityInt = 0;
        while (quantity_sc.hasNextInt()) {
            quantityInt = quantity_sc.nextInt();
        }
        return quantityInt;
    }
    
    /**
     * Метод для создания медиафайла на основе видеофрагмента
     *
     * @return видео выбранного урока
     */
    public Media getMedia() {
        File file_video = new File(adress + "Video.mp4");
        String mediaPath = file_video.toURI().toASCIIString();
        Media media = new Media(mediaPath);
        return media;
    }

    /**
     * Метод для считывания словарика
     *
     * @return список строк, каждый элемент ктр содержит один абзац из файла
     * @throws FileNotFoundException
     */
    public List readVocab() throws FileNotFoundException {
        List<String> list = new ArrayList<String>();
        File file_vocab = new File(adress + "vocab.txt");
        try (Scanner in = new Scanner(file_vocab)) {
            while (in.hasNext()) {
                list.add(in.nextLine());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Метод для считывания текста на французском
     *
     * @return список строк, каждый элемент ктр содержит один абзац из файла
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List readTextFR() throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<String>();
        File fran = new File(adress + "textFR.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fran), "Unicode"));
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            list.add(tmp);
        }
        return list;
    }

    /**
     * Метод для считывания текста на руском
     *
     * @return список строк, каждый элемент ктр содержит один абзац из файла
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List readTextRU() throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<String>();
        File rus = new File(adress + "textRU.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(rus), "Unicode"));
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            list.add(tmp);
        }
        return list;
    }

    /**
     * Метод для считывания заголовка
     * 
     * @return строку, содержащую заголовок
     * @throws FileNotFoundException
     */
    public String readTitle() throws FileNotFoundException {//считываем заголовок 
        File head = new File(adress + "title.txt");
        FileReader reader_head = new FileReader(head);
        Scanner fran_sc = new Scanner(reader_head);
        String fran_text = "";
        while (fran_sc.hasNextLine()) {
            fran_text += fran_sc.nextLine();
        }
        return fran_text;
    }

    /**
     * Метод для чтения Задания 1
     *
     * @return список строк, каждый элемент ктр содержит одину строку из файла,
     *         первый элемнт типа int - количество вопросов
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public List<String> readTask1() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        List<String> list = new ArrayList<String>();
        this.adress = "data/L" + lesNum + "/";
        System.out.println(lesNum + 1);
        File task1 = new File(adress + "task1.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(task1), "Unicode"));
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            list.add(tmp);
        }
        return list;
    }

    /**
     * Метод для чтения Задания 2
     *
     * @return список строк, каждый элемент ктр содержит один абзац из файла,
     *         первый элемнт типа int - количество пропусков в тексте
     * @throws IOException
     */
    public List<String> readTask2() throws IOException {
        List<String> list = new ArrayList<String>();
        this.adress = "data/L" + lesNum + "/";
        System.out.println(lesNum + 1);
        File task2 = new File(adress + "task2.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(task2), "Unicode"));
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null) {
            list.add(tmp);
        }
        return list;
    }

    /**
     * Метод для считывания французских субтитров
     *
     * @return список пар в формате "значение - ключ" те "строка - время".
     *         Пример: "five - 5"
     * @throws FileNotFoundException
     */
    public List<String> readSubFR() throws FileNotFoundException {
        List<String> list = new ArrayList<String>();
        File task2 = new File(adress + "subFR.txt");
        try (Scanner in = new Scanner(task2)) {
            while (in.hasNext()) {
                list.add(in.nextLine());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * Метод для считывания русских субтитров
     *
     * @return список пар в формате "значение - ключ" те "строка - время".
     *         Пример: "пять - 5"
     * @throws FileNotFoundException
     */
    public List<String> readSubRU() throws FileNotFoundException {
        List<String> list = new ArrayList<String>();
        File task2 = new File(adress + "subRU.txt");
        try (Scanner in = new Scanner(task2)) {
            while (in.hasNext()) {
                list.add(in.nextLine());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    /**
     * Метод для считывания картинок для Задание 3
     * @return ArrayList с картинками
     * @throws FileNotFoundException 
     */
    
    public List<Image> readPicturesList() throws FileNotFoundException {
        List<Image> imagePictureList = new ArrayList<Image>();
        for (int i = 1; i <= 10; i++) {
            FileInputStream f = new FileInputStream(adress + "/picture/sc" + i + ".png");
            Image sc = new Image(f, 300, 200, false, false);
            imagePictureList.add(sc);
        }
        return imagePictureList;
    }
    
//    public void clear() throws FileNotFoundException {
//        List<String> list = new ArrayList<String>();
//        File task2 = new File(adress + "subRU.txt");
//        try (Scanner in = new Scanner(task2)) {
//            while (in.hasNext()) {
//                list.add(in.nextLine());
//            }
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//        
//    }

//    public LinkedList <TextField> Answers(int asw) {
//        int i = 0;
//        LinkedList <TextField> answers = new LinkedList <TextField>();
//        for (i = 1; i <= asw; i++) {
//            TextField tf = new TextField();
//            answers.add(tf);
//        }
//        return answers;
//    }
}
