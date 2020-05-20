package company;

public class Historia {
    String tab[];
    int index;
    public Historia(){
        tab = new String[1024];
        index = 0;
    }

    public void dodaj(String url){
        tab[index] = url;
        index++;
    }
    public void wyczysc(){
        for(int i = 0;i < index+1; i++){
            tab[i] = null;
        }
        index = 0;
    }
    public void wypisz(){
        for(int i = 0;i < index ; i++){
            System.out.println(tab[i]);
        }
    }
}
