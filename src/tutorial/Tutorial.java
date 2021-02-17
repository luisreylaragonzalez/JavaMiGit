
package tutorial;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;



import java.util.logging.Logger;

public class Tutorial {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
            
Tutorial tut = new Tutorial();

tut.runTimer();
//Consultar();
        
        }

     private static void Consultar() throws ClassNotFoundException, SQLException {

        PreparedStatement pps = null;
         
        
        
        ConexionSerial mics = new ConexionSerial();
         
          mics.conectar("COM4");
          //  System.out.println(comboPuertos.getSelectedItem().toString());
            mics.iniciarIO();
            mics.initListener();
            
            if(mics.getConectado()){
                System.out.println("Conectado"); 
           //     reiniciarHilo();
            //    hilo.start();
            }else{
            System.out.println("NOO Conectado");
            //    hilo.stop();
            //    statusHilo=false;
            } 
         
         
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection con = null;
        con = DriverManager.getConnection("jdbc:mysql://132.145.205.156:3306/admin_default", "admin_default", "Pau123456");

        String sql = "";

        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //  System.out.println("Hora y fecha: "+hourdateFormat.format(date));
        
        Calendar calendar = Calendar.getInstance();
        
        
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.SECOND,15);  // numero de horas a añadir, o restar en caso de horas<0
      //  System.out.println(calendar.getTime()); // Devuelve el objeto Date con las nuevas horas añadidas
      
        DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //  System.out.println(hourdateFormat2.format(calendar.getTime()));

        sql = "select * from actividades where fecha >= '" + hourdateFormat.format(date) + "'" +
                " and fecha <= '"+  hourdateFormat2.format(calendar.getTime())+ "' and estatus = 0";
        
        
        System.out.println("Mi sql--->: " + sql);
        String[] dato = new String[3];
        try {
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                
               

                System.out.print(rs.getString(1)+" ");
               // System.out.print(rs.getString(2)+" ");
               // System.out.print(rs.getString(3)+" ");
                
                if (dato[2].equals("Apagar led")) {
                    System.out.println("Apagar en Arduino");
                    mics.escribir(1);
                    
                }
                if (dato[2].equals("Encender led")) {
                    System.out.println("Encender en Arduino");
                    mics.escribir(2);
                }
                
                
              //  System.out.print(rs.getString(4));
              //  System.out.println(rs.getString(5));
                
                //System.out.println("UPDATE actividades SET estatus=1 WHERE id="+rs.getString(1));
                
                //----
                String sql2 = "";
                sql2 = "update actividades set estatus = 1 where id =" + rs.getString(1);
             //   System.out.println("Mi sql---> " + sql2);

                try {
                    java.sql.PreparedStatement psd = con.prepareStatement(sql2);

                    int n = psd.executeUpdate(sql2);

                } catch (SQLException ex) {

                    System.out.println(ex);
                }

                //---
                
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

  
Timer timer = new Timer();


int i = 0;

int vigilante=15; // vigilante indica cada cuantos segundos deseo ejecutar **algo**

TimerTask task = new TimerTask(){
	public void run(){
		String time = null;
            try {
                time = getTime(i,vigilante);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Tutorial.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Tutorial.class.getName()).log(Level.SEVERE, null, ex);
            }
	//	System.out.println(time);
		i++;

	}
};

public void runTimer(){
	timer.schedule(task, 0, 1000 );
}


static String getTime(int sec, int vigilante) throws ClassNotFoundException, SQLException
{
    //if we have hours minutes and seconds
    int hours = 0;
    int remainderOfHours = 0;
    int minutes = 0;
    int seconds = 0;
    int misec=0;
    
    
    Date date = new Date();
        
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        
       // System.out.println(hourdateFormat.format(date));
        
         hourdateFormat = new SimpleDateFormat("HH");
        int totseg= (Integer.parseInt(hourdateFormat.format(date))*3600);
        
         hourdateFormat = new SimpleDateFormat("mm");
         totseg+= (Integer.parseInt(hourdateFormat.format(date))*60);
         
         hourdateFormat = new SimpleDateFormat("ss");
         totseg+= (Integer.parseInt(hourdateFormat.format(date)));
         
     //    System.out.println(totseg+"-------"+(totseg+36000));
        sec=totseg;
  //  System.out.println(sec);
    
    if (sec % vigilante == 0){
        // System.out.println("Funciona");
         Consultar();
         
        }
    
    if (sec >= 3600) // if we have an hour or more     
    {
        hours = sec / 3600;               
        remainderOfHours = sec % 3600;        // could be more or less than a min

        if (remainderOfHours >= 60)   //check if remainder is more or equal to a min
        {
            minutes = remainderOfHours / 60;
            seconds = remainderOfHours % 60;
        }
        else
        {                       // if it's less than a min
            seconds = remainderOfHours;
        }
    }
    // if we have a min or more
    else if (sec >= 60)                
    {
        
        hours = 0;               //62
        minutes = sec / 60;
        seconds = sec % 60;
    }
    //if we have just seconds
    else if (sec < 60)
    {
     
    }
//i get integer hour minuite second. i want to transform them to strings:
    
    String strHours;
    String strMins; 
    String strSecs; 

    if(seconds < 10)
    	strSecs = "0" + Integer.toString(seconds);
    else
    	strSecs = Integer.toString(seconds);
   
    if(minutes < 10)
   	 strMins = "0" + Integer.toString(minutes);
   else
	   strMins = Integer.toString(minutes);
    
    if(hours < 10)
    	strHours = "0" + Integer.toString(hours);
      else
    	  strHours = Integer.toString(hours);
    	
        
    String time = strHours + ":" + strMins + ":" + strSecs;
    return time;
}



}