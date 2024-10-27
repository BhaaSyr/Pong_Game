package game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bahaa
 */
class data{
    private static String path = "src\\files\\data.txt";
    public static String[] ps = {"player1","player2"};    //names
    public static int[] scor = {0,0};                     //sors
    public static int music = 50;                         //settings
    public static int backin = 0;                         
    public static int diff= 1;                            //0=easy,1=medium,2=hard//
    public data(){
       
    }
    public void load(){
     String l = "";
     String l2 = "";
        try(BufferedReader in = new BufferedReader(new FileReader(path))){
            while((l=in.readLine())!=null){
                l2 = l2+l;
                
            }
            if (l2!=null) {
            String[] ll = l2.split("`z/");
                
            ps[0] = ll[0].substring(2);
            ps[1] = ll[1].substring(2);
            scor[0] = Integer.parseInt(ll[2].substring(2));
            scor[1] =Integer.parseInt(ll[3].substring(2));
            music = Integer.parseInt(ll[4].substring(2));
            backin = Integer.parseInt(ll[5].substring(2));
            diff = Integer.parseInt(ll[6].substring(2));
            } 
            
        }catch(IOException e){
            System.out.println(e.getCause());
        }  
    }
    public void save(){
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(path))){
           out.writeUTF(ps[0]+"`z/");
           out.writeUTF(ps[1]+"`z/");
           out.writeUTF(scor[0]+"`z/");
           out.writeUTF(scor[1]+"`z/");
           out.writeUTF(music+"`z/");
           out.writeUTF(backin+"`z/");
           out.writeUTF(diff+"`z/");

        }catch(IOException e){
            System.out.println(e);
        }  
    }
   public String[] ps(){
        
       return ps;
   }
   public int[] scor(){
        
       return scor;
   }
   public int music(){
        
       return music;
   }
   public int back(){
        
       return backin;
   }
   public int diff(){
        
       return diff;
   }
   public void ps(String[] d){
       if (d.length==2) {
           ps = d;
       }
 
       
   }
   public void scor(int[] d){
       if (d.length==2) {
           scor = d;
       }
        
       
   }
   public void music(int d){
        
       music = d;
   }
   public void back(int d){
        
       backin = d;
   }
   public void diff(int d){
        
       diff = d;
   }
   
}
public class menu extends JPanel implements ActionListener {
    private JButton[] btn;
    private JLabel[] lab; 
    data my = new data();

    private final int W = 1300,H = 700;
    private final String[] str = {"Play", "Settings","Edit players name","reset scores to 0", "Exit"};
    public menu()  {

       
        
        this.setPreferredSize(new Dimension(1300, 700));
        this.setBackground(new Color(0, 51, 51));
        this.setMinimumSize(new Dimension(1300, 700));
        
        GroupLayout jPanel1Layout = new GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1300, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        
        
        btn = new JButton[5];
        lab = new JLabel[5];
        
         lab[0] = new JLabel(my.ps()[0]);
         lab[1] = new JLabel("Heightest score :"+my.scor()[0]);
         lab[2] = new JLabel(my.ps()[1]);
         lab[3] = new JLabel("Heightest score :"+my.scor()[1]);
         lab[4] = new JLabel("<Wellcome>");
        lab[0].setBounds(20, 20,200,40);
        lab[1].setBounds(20, 50,500,40);
        lab[2].setBounds(W-300, 20, 200, 50);
        lab[3].setBounds(W-300, 50,500,50);
        lab[4].setBounds(W/2-100, 80,300,50);
        for (JLabel labs:lab) { 
        labs.setFont(new Font("Tempus Sans ITC", 1, 24));
        labs.setForeground(new Color(255, 0, 102));
        this.add(labs);
        }
        lab[4].setForeground(Color.WHITE/*new Color(102,51,255)*/);
        lab[4].setFont(new Font("Tempus Sans ITC", 1, 38));
        
        
       
        for (int i = 0; i < btn.length; i++) {
            
            btn[i] = new JButton(str[i]);
        btn[i].setBackground(new Color(0, 51, 51));
        btn[i].setFont(new Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        btn[i].setForeground(new Color(255, 0, 102));
        //btn[i].setBorder(null);
        btn[i].setFocusPainted(false);
        //btn[i].setBorder(BorderFactory.createBevelBorder(i,Color.DARK_GRAY,Color.GRAY));
        this.add(btn[i]);
            if (i==2||i==3) {
                btn[i].setBounds((W/2)-140, 200+i*90, 280, 40);
            }else{
                btn[i].setBounds((W/2)-85, 200+i*90, 170, 40);}
        
        btn[i].getAccessibleContext().setAccessibleParent(this);
        btn[i].addActionListener(this);
           

        }
        

    }

//    public void paint(Graphics g){
//        
//        Graphics2D g2 = (Graphics2D) g;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn[0])) {
            
                panel pan;
            try {
                pan = new panel();
                Game.swit(pan,this);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        
           
          
            
        }
        if (e.getSource().equals(btn[1])) {
            settings set = new settings();
            Game.swit(set,this);
        }
        if (e.getSource().equals(btn[2])) {
          String jo1 =  JOptionPane.showInputDialog(this, "Enter player1 name:",my.ps()[0]);
          String jo2 =  JOptionPane.showInputDialog(this, "Enter player2 name:",my.ps()[1]);
             // JOptionPane.showInputDialog(comb, e, TOOL_TIP_TEXT_KEY, HEIGHT, icon, btn, LEFT_ALIGNMENT)
             if ((jo1.length()>2)&&(jo2.length()>2)) {
                my.ps(new String[] {jo1,jo2});
                this.lab[0].setText(jo1);
                 this.lab[2].setText(jo2);
                 my.save();
            }
            
        }
        if (e.getSource().equals(btn[3])) {
            my.scor(new int[]{0,0});
            this.lab[1].setText("0");
            this.lab[4].setText("0");
            my.save();
        }
        if (e.getSource().equals(btn[4])) {
            System.exit(0);
        }
        
        
        
           }

}
////////////////////////////////////////////////////////////////////////////////
class settings extends JPanel implements ActionListener,ChangeListener{
    private JButton[] btn;
    private JSlider jS;
    private JComboBox comb,comb2;
    private JLabel[] lab; 
    data my = new data();
    private final String[] str = {"Save","Back"};
    private final String[] str2 = {"Music :"+my.music(),"background :","Difficulty :","<Settings>"};
    private final String[] def = {"Easy","Medium","Hard"};
    private final String[] back = {"src\\imgs\\back\\","src\\imgs\\back\\","src\\imgs\\?"};
    private final int W = 1300,H = 700;
    public settings(){
        
        this.setPreferredSize(new Dimension(1300, 700));
        this.setBackground(new Color(0, 51, 51));
        this.setMinimumSize(new Dimension(1300, 700));
        GroupLayout jPanel1Layout = new GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1300, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        
        btn = new JButton[2];
        lab = new JLabel[4];
        comb = new JComboBox();
        comb2 = new JComboBox();
        comb.setFont(new Font("Gill Sans MT", 0, 18)); // NOI18N
        comb.setForeground(new Color(0, 51, 51));
        comb.setBounds((W/2)+70, 270, 170, 30);
        //comb.setEnabled(true);
        comb.setModel(new DefaultComboBoxModel<>(str));
        comb.setSelectedIndex(my.back());
        comb2.setFont(new Font("Gill Sans MT", 0, 18)); // NOI18N
        comb2.setForeground(new Color(0, 51, 51));
        comb2.setBounds((W/2)+70, 340, 170, 30);
        comb2.setModel(new DefaultComboBoxModel<>( def));
        comb2.setSelectedIndex(my.diff());
        this.add(comb);
        this.add(comb2);
        
        jS = new JSlider();
        jS.setPaintLabels(false);
        jS.setPaintTicks(false);
        jS.setOpaque(false);
        jS.setBounds((W/2)+50, 210, 200, 20);
        jS.setValue(my.music());
        jS.addChangeListener(this);
        
        
        this.add(jS);
        
         for (int i = 0; i < lab.length; i++) {
        lab[i] = new JLabel(str2[i]);
        lab[i].setFont(new Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        lab[i].setForeground(new Color(255, 0, 102));
        this.add(lab[i]);
        lab[i].setBounds((W/2)-150, 200+i*70, 200, 40);
        }
        lab[3].setFont(new Font("Copperplate Gothic Light", 1, 38)); // NOI18N
        lab[3].setForeground(new Color(102,51,255));
        lab[3].setBounds((W/2)-125, 80, 250, 40);
 
        for (int i = 0; i < btn.length; i++) {
        btn[i] = new JButton(str[i]);
        btn[i].setBackground(new Color(0, 51, 51));
        btn[i].setFont(new Font("Copperplate Gothic Light", 1, 24)); // NOI18N
        btn[i].setForeground(new Color(255, 0, 102));
        btn[i].setFocusPainted(false);
        //btn[i].setBorder(BorderFactory.createBevelBorder(i,Color.DARK_GRAY,Color.GRAY));
        //btn[i].setBorderPainted(true);
        this.add(btn[i]);
        btn[i].setBounds((W/2)-100, 500+i*70, 200, 40);
        btn[i].getAccessibleContext().setAccessibleParent(this);
        btn[i].addActionListener(this);
        }
        

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn[1])) {
            
          menu my = new menu();
          Game.swit(my, this);
            
        }
        if (e.getSource().equals(btn[0])) {
            my.music(this.jS.getValue());
            my.back(this.comb.getSelectedIndex());
            my.diff(this.comb2.getSelectedIndex());
             my.save();
        }
          }

    @Override
    public void stateChanged(ChangeEvent e) {
        lab[0].setText("Music : "+jS.getValue());
        lab[0].repaint();
         }
} 
