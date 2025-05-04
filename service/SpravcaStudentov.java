1
package service;

import java.util.*;
import model.*;

public class SpravcaStudentov {
    private List<Student> studenti = new ArrayList<>();
    private int dalsieId = 1;

    public void nacitajZDatabazy() {
        DatabaseService db = new DatabaseService();
        studenti = db.nacitajStudentov();
        for (Student s: studenti) if (s.getId()>=dalsieId) dalsieId = s.getId()+1;
    }

    public void ulozCeleDoDatabazy() {
        new DatabaseService().ulozCelyZoznam(studenti);
    }

    public int getNoveId() { return dalsieId++; }

    public void pridajStudenta(Student s){ studenti.add(s); }

    public boolean odstranStudenta(int id){
        for (int i=0;i<studenti.size();i++){
            if (studenti.get(i).getId()==id){ studenti.remove(i); return true;}
        }
        return false;
    }

    public Student najdiStudentaPodlaId(int id){
        for (Student s: studenti) if (s.getId()==id) return s;
        return null;
    }

    public List<Student> getStudentiAbecedne(char typ){
        List<Student> v=new ArrayList<>();
        for (Student s: studenti){
            if ((typ=='T' && s instanceof TelekomStudent)||(typ=='K'&& s instanceof KyberStudent)) v.add(s);
        }
        // bublinové
        for(int i=0;i<v.size()-1;i++){
            for(int j=i+1;j<v.size();j++){
                if(v.get(i).getPriezvisko().compareToIgnoreCase(v.get(j).getPriezvisko())>0){
                    Student tmp=v.get(i); v.set(i,v.get(j)); v.set(j,tmp);
                }
            }
        }
        return v;
    }

    public double getPriemerSkupiny(char typ){
        double s=0; int p=0;
        for(Student st: studenti){
            if((typ=='T'&& st instanceof TelekomStudent)||(typ=='K'&& st instanceof KyberStudent)){
                s+=st.getPriemerZnamok(); p++;
            }
        }
        return p==0?0:s/p;
    }

    public long getPocetSkupiny(char typ){
        int p=0;
        for(Student st: studenti){
            if((typ=='T'&& st instanceof TelekomStudent)||(typ=='K'&& st instanceof KyberStudent)) p++;
        }
        return p;
    }

    public Student getNajlepsiStudent(){
        if(studenti.isEmpty()) return null;
        Student best=studenti.get(0);
        for(Student s: studenti) if(s.getPriemerZnamok()>best.getPriemerZnamok()) best=s;
        return best;
    }

    public Student getNajhorsiStudent(){
        if(studenti.isEmpty()) return null;
        Student w=studenti.get(0);
        for(Student s: studenti) if(s.getPriemerZnamok()<w.getPriemerZnamok()) w=s;
        return w;
    }

    public boolean ulozStudentaDoSuboru(int id,String subor){
        Student s=najdiStudentaPodlaId(id);
        if(s==null) return false;
        try(java.io.PrintWriter pw=new java.io.PrintWriter(subor)){
            pw.print(s.getId()+";"+s.getMeno()+";"+s.getPriezvisko()+";"+s.getDatumNarodenia()+";"+
                    (s instanceof TelekomStudent?"T":"K")+";");
            for(int z:s.getZnamky()) pw.print(z+",");
            return true;
        }catch(Exception e){return false;}
    }

    public boolean nacitajStudentaZoSuboru(String subor){
        try(java.util.Scanner sc=new java.util.Scanner(new java.io.File(subor))){
            if(!sc.hasNextLine()) return false;
            String r=sc.nextLine();
            String[] c=r.split(";");
            int id=Integer.parseInt(c[0]);
            String meno=c[1],priez=c[2];
            java.sql.Date dat=java.sql.Date.valueOf(c[3]);
            char typ=c[4].charAt(0);
            Student s= typ=='T'? new TelekomStudent(id,meno,priez,dat):new KyberStudent(id,meno,priez,dat);
            if(c.length>5){
                for(String z:c[5].split(",")) if(!z.isBlank()) s.pridajZnamku(Integer.parseInt(z));
            }
            studenti.add(s);
            if(id>=dalsieId) dalsieId=id+1;
            return true;
        }catch(Exception e){return false;}
    }

    public List<Student> getVsetci(){ return studenti; }
}
