
import java.util.Scanner;
import model.*;
import service.SpravcaStudentov;

public class Main {
    public static void main(String[] args) {
        SpravcaStudentov spravca=new SpravcaStudentov();
        spravca.nacitajZDatabazy();
        Scanner sc=new Scanner(System.in);
        boolean bezi=true;
        while(bezi){
            System.out.println("\n--- MENU ---");
            System.out.println("1 Pridaj studenta");
            System.out.println("2 Pridaj znamku");
            System.out.println("3 Odstran studenta");
            System.out.println("4 Zobraz studenta");
            System.out.println("5 Dovednost studenta");
            System.out.println("6 Abecedny vypis skupin");
            System.out.println("7 Priemery skupin");
            System.out.println("8 Pocty skupin");
            System.out.println("9 Uloz studenta do suboru");
            System.out.println("10 Nacitaj studenta zo suboru");
            System.out.println("0 Koniec");
            int v=sc.nextInt();sc.nextLine();
            switch(v){
                case 1:{
                    System.out.print("Meno: ");String m=sc.nextLine();
                    System.out.print("Priezvisko: ");String p=sc.nextLine();
                    System.out.print("Datum (yyyy-mm-dd): ");String d=sc.nextLine();
                    System.out.print("Typ (T/K): ");char t=sc.nextLine().toUpperCase().charAt(0);
                    int id=spravca.getNoveId();
                    try{
                        java.sql.Date dat=java.sql.Date.valueOf(d);
                        Student s=t=='T'?new TelekomStudent(id,m,p,dat):new KyberStudent(id,m,p,dat);
                        spravca.pridajStudenta(s);
                    }catch(Exception e){System.out.println("Zly datum");}
                }break;
                case 2:{
                    System.out.print("ID: ");int id=sc.nextInt();
                    System.out.print("Znamka: ");int z=sc.nextInt();sc.nextLine();
                    Student s=spravca.najdiStudentaPodlaId(id);
                    if(s!=null){s.pridajZnamku(z);} else System.out.println("Nenajdeny");
                }break;
                case 3:{
                    System.out.print("ID: ");int id=sc.nextInt();sc.nextLine();
                    if(spravca.odstranStudenta(id)) System.out.println("Zmazany"); else System.out.println("Neni");
                }break;
                case 4:{
                    System.out.print("ID: ");int id=sc.nextInt();sc.nextLine();
                    Student s=spravca.najdiStudentaPodlaId(id);
                    if(s!=null) System.out.println(s.getMeno()+" "+s.getPriezvisko()+" priemer "+s.getPriemerZnamok());
                    else System.out.println("Nenajdeny");
                }break;
                case 5:{
                    System.out.print("ID: ");int id=sc.nextInt();sc.nextLine();
                    Student s=spravca.najdiStudentaPodlaId(id);
                    if(s!=null) System.out.println(s.getTransformovaneMeno());
                }break;
                case 6:{
                    for(Student s:spravca.getStudentiAbecedne('T'))
                        System.out.println("T "+s.getPriezvisko()+" "+s.getMeno());
                    for(Student s:spravca.getStudentiAbecedne('K'))
                        System.out.println("K "+s.getPriezvisko()+" "+s.getMeno());
                }break;
                case 7:{
                    System.out.println("T priemer "+spravca.getPriemerSkupiny('T'));
                    System.out.println("K priemer "+spravca.getPriemerSkupiny('K'));
                }break;
                case 8:{
                    System.out.println("T pocet "+spravca.getPocetSkupiny('T'));
                    System.out.println("K pocet "+spravca.getPocetSkupiny('K'));
                }break;
                case 9:{
                    System.out.print("ID: ");int id=sc.nextInt();sc.nextLine();
                    spravca.ulozStudentaDoSuboru(id,"vybrany.txt");
                }break;
                case 10:{
                    spravca.nacitajStudentaZoSuboru("vybrany.txt");
                }break;
                case 0:{
                    bezi=false;
                    spravca.ulozCeleDoDatabazy();
                }break;
                default:System.out.println("Zla volba");
            }
        }
    }
}
