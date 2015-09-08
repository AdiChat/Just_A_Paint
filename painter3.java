/*
The MIT License (MIT)

Copyright (c) 2015 Aditya Chatterjee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class painter3 extends Frame implements Serializable {

   public int x1,y1,x2,y2,mode,lx1,lx2,ly1,ly2;
   public static Vector oV,foV,lV,rV,frV,dV,flV,main,bgV;
   public static Button color,remove,line,oval,fillov,rect,fillrect,bg,fl;
   public static Label info,cord1,cord2;
   public static MenuItem New,open,save,saveAs,close,exit,me;
   public static painter3 a;
   public static Color c,bgc;
   public static String name = null;

   public static void main (String [] args) {
      a = new painter3();
      a.show();
      main = new Vector();
      if (args.length!=0&&args[0]!=null) {
         System.out.println("Open..."+args[0]);
         try {
            System.out.print("Reading file...");
            FileInputStream s = new FileInputStream (args[0]);
            System.out.println("done");
            System.out.print("Getting object stream...");
            ObjectInputStream ss = new ObjectInputStream (s);
            System.out.println("done");
            System.out.print("Reading vector...");
            main = (Vector)ss.readObject();
            System.out.println("done");
            System.out.print("Rendering image...");
            a.openIt(main);
            System.out.println("done");
         } catch (Exception dsa) {
           a.cantOpen();
        }
      }
   }

   public painter3 () {
      setSize(800,575);
      setTitle("Just A Paint by Aditya Chatterjee");
      setBackground(new Color(192,192,192));
      setCursor(Cursor.CROSSHAIR_CURSOR);
      addWindowListener (new WindowAdapter () {public void windowClosing (WindowEvent e) {System.exit(0);}public void windowDeiconified (WindowEvent e) {repaint();}public void windowActivated (WindowEvent e) { repaint();}});
      setLayout(null);
      mouseListener l = new mouseListener ();
      addMouseListener(l);
      addMouseMotionListener(l);
      MenuBar bar = new MenuBar();
      setMenuBar(bar);
      Menu file = new Menu("FILE");
      Menu mee = new Menu("ABOUT");
      New = new MenuItem ("New", new MenuShortcut(110));
      open = new MenuItem ("Open...",new MenuShortcut(111));
      close = new MenuItem ("Close",new MenuShortcut(99));
      save = new MenuItem ("Save",new MenuShortcut(115));
      saveAs = new MenuItem("Save As...");//,new MenuShortcut(118));
      exit = new MenuItem ("Exit",new MenuShortcut(101));
      me = new MenuItem ("About Author");
      file.add(New);
      file.add(open);
      file.add(save);
      file.add(saveAs);
      file.add(close);
      file.add(exit);
      file.insertSeparator(5);
      mee.add(me);
      bar.add(file);
      bar.add(mee);
      bgV = new Vector ();
      oV = new Vector ();
      foV = new Vector ();
      lV = new Vector ();
      rV = new Vector ();
      frV = new Vector ();
      flV = new Vector ();
      info = new Label("Drawing Mode: none");
      cord1 = new Label("Coordinates: ");
      cord2 = new Label("Dimensions: ");
      remove = new Button("CLEAR");
      line = new Button("Line");
      oval = new Button("Oval");
      fillov = new Button("Filled Oval");
      rect = new Button("Rectangle");
      fillrect = new Button("Filled Rectangle");
      bg = new Button("B/G Color");
      color = new Button("Drawing Color");
      fl = new Button("Free Hand Curve");
      remove.addActionListener (new buttonListener ());
      bg.addActionListener(new buttonListener () );
      line.addActionListener(new buttonListener () );
      oval.addActionListener(new buttonListener () );
      fillov.addActionListener(new buttonListener () );
      rect.addActionListener(new buttonListener () );
      fillrect.addActionListener(new buttonListener () );
      color.addActionListener(new buttonListener () );
      fl.addActionListener(new buttonListener () );
      New.addActionListener(new buttonListener () );
      open.addActionListener(new buttonListener () );
      save.addActionListener(new buttonListener () );
      saveAs.addActionListener(new buttonListener () );
      close.addActionListener(new buttonListener () );
      exit.addActionListener(new buttonListener () );
      me.addActionListener(new buttonListener () );
      add(line);
      add(oval);
      add(fillov);
      add(rect);
      add(fillrect);
      add(remove);
      add(bg);
      add(info);
      add(color);
      add(cord1);
      add(cord2);
      add(fl);
      remove.setBounds(50,50,70,20);
      line.setBounds(125,50,70,20);
      oval.setBounds(200,50,70,20);
      fillov.setBounds(275,50,100,20);
      rect.setBounds(380,50,70,20);
      fillrect.setBounds(455,50,100,20);
      fl.setBounds(560,50,100,20);
      bg.setBounds(700,545,90,20);
      color.setBounds(610,545,90,20);
      info.setBounds(20,545,200,20);
      cord1.setBounds(220,545,150,20);
      cord2.setBounds(370,545,200,20);
      x1=x2=lx1=lx2=50;
      y1=y2=ly1=ly2=100;
      mode = 0;
      c = Color.black;
      bgc = Color.white;
   }
   public void openIt (Vector main) {
      oV = (Vector) main.elementAt(0);
      foV = (Vector) main.elementAt(1);   
      lV = (Vector) main.elementAt(2);   
      rV = (Vector) main.elementAt(3);   
      frV = (Vector) main.elementAt(4);   
      dV = (Vector) main.elementAt(5);   
      flV = (Vector) main.elementAt(6);
      bgc = (Color) (((Vector) main.elementAt(7)).elementAt(0));
      repaint();
   }
   public void cantOpen () {
      System.out.println("failed");
      repaint();
      JOptionPane.showMessageDialog(null,"File format is not supported","Error Message",JOptionPane.ERROR_MESSAGE);
      repaint();
   }
   private class buttonListener implements ActionListener {

      public void actionPerformed (ActionEvent e) {

         if (e.getSource() == remove) {
            oV.removeAllElements();
            foV.removeAllElements();
            lV.removeAllElements();
            rV.removeAllElements();
            frV.removeAllElements();
            flV.removeAllElements();
            cord2.setText("Dimensions: W > 0 H > 0");
            repaint();
         }

         if (e.getSource() == New || e.getSource() == close) {
            oV.removeAllElements();
            foV.removeAllElements();
            lV.removeAllElements();
            rV.removeAllElements();
            frV.removeAllElements();
            flV.removeAllElements();
            mode = 0;
            info.setText("Drawing Mode: none");
            cord2.setText("Dimensions: W > 0 H > 0");
            c = Color.black;
            bgc = Color.white;
            name = null;
            repaint();
         }

         if (e.getSource() == bg) {
            bgc = JColorChooser.showDialog(null,"My Colour Set",bgc);
            if (bgc == null) {
               bgc = Color.white;
            }
            repaint();
         }

         if (e.getSource() == me) {
            JOptionPane.showMessageDialog(null,"Just Aonther Paint by Aditya Chatterjee\nLanguage used: Java SE 8\nOpen Source Software\nMIT License","Just for your curiosity",JOptionPane.INFORMATION_MESSAGE);
            repaint();
         }

         if (e.getSource() == open) {
            FileDialog f = new FileDialog(a,"Your File Store",FileDialog.LOAD);
            f.setDirectory("C:\\WINDOWS\\Desktop");
            f.setFile("*.vim");
            f.show();
            update(a.getGraphics());
            name = f.getDirectory()+f.getFile();
            if (f.getFile() != null) {
               System.out.println("Open..."+name);
               try {
                  System.out.print("Reading file...");
                  FileInputStream s = new FileInputStream (name);
                  System.out.println("done");
                  System.out.print("Getting object stream...");
                  ObjectInputStream ss = new ObjectInputStream (s);
                  System.out.println("done");
                  System.out.print("Reading vector...");
                  main = (Vector)ss.readObject();
                  System.out.println("done");
                  System.out.print("Rendering image...");
                  openIt(main);
                  System.out.println("done");
               } catch (Exception dsa) {
                  cantOpen();
               }
            } else { name = null; }
            repaint();
         }

         if (e.getSource() == exit) {
            System.exit(0);
         }

         if (e.getSource() == color) {
            c = JColorChooser.showDialog(null,"My Colour Set",c);
            if (c == null) {
               c = Color.black;
            }
            repaint();
         }

         if (e.getSource() == line) {
            mode = 1;
            info.setText("Drawing Mode: Line");
         }

         if (e.getSource() == oval) {
            mode = 2;
            info.setText("Drawing Mode: Oval");
         }

         if (e.getSource() == fillov) {
            mode = 3;
            info.setText("Drawing Mode: Filled Oval");
         }

         if (e.getSource() == rect) {
            mode = 4;
            info.setText("Drawing Mode: Rectangle");
         }

         if (e.getSource() == fillrect) {
            mode = 5;
            info.setText("Drawing Mode: Filled Rectangle");
         }

         if (e.getSource() == fl) {
            mode = 6;
            info.setText("Drawing Mode: Free Hand Curve");
         }

         if (e.getSource() == saveAs) {
            FileDialog dia = new FileDialog(a,"My File Store",FileDialog.SAVE);
            dia.setDirectory("C:\\WINDOWS\\Desktop");
            dia.setFile("*.vim");
            dia.show();
            name = dia.getDirectory()+dia.getFile();
            if (dia.getFile() != null) {
               main.removeAllElements();
               main.addElement(oV);
               main.addElement(foV);
               main.addElement(lV);
               main.addElement(rV);
               main.addElement(frV);
               main.addElement(dV);
               main.addElement(flV);
               bgV.addElement(bgc);
               main.addElement(bgV);
               try {
                  FileOutputStream ff = new FileOutputStream (name);
                  ObjectOutputStream oo = new ObjectOutputStream (ff);
                  oo.writeObject(main);
               } catch (Exception jd) {}
            } else { name = null; }
            repaint();
         }

         if (e.getSource() == save) {
            if ( name != null ) {
               main.removeAllElements();
               main.addElement(oV);
               main.addElement(foV);
               main.addElement(lV);
               main.addElement(rV);
               main.addElement(frV);
               main.addElement(dV);
               main.addElement(flV);
               bgV.addElement(bgc);
               main.addElement(bgV);
               try {
                  FileOutputStream ff = new FileOutputStream (name);
                  ObjectOutputStream oo = new ObjectOutputStream (ff);
                  oo.writeObject(main);
               } catch (Exception jd) {}
            } else {
               FileDialog dia = new FileDialog(a,"My File Store",FileDialog.SAVE);
               dia.setDirectory("C:\\WINDOWS\\Desktop");
               dia.setFile("*.vim");
               dia.show();
               name = dia.getDirectory()+dia.getFile();
               main.removeAllElements();
               main.addElement(oV);
               main.addElement(foV);
               main.addElement(lV);
               main.addElement(rV);
               main.addElement(frV);
               main.addElement(dV);
               main.addElement(flV);
               bgV.addElement(bgc);
               main.addElement(bgV);
               try {
                  FileOutputStream ff = new FileOutputStream (name);
                  ObjectOutputStream oo = new ObjectOutputStream (ff);
                  oo.writeObject(main);
               } catch (Exception jd) {}
               repaint();
            }
         }

      }

   }

   private class mouseListener extends MouseAdapter implements MouseMotionListener  {

      public void mousePressed (MouseEvent e) {
         if ((e.getX()>=50&&e.getX()<=750)&&(e.getY()>=100&&e.getY()<=525)) {
            x1 = lx1 = lx2 = e.getX();
            y1 = ly1 = ly2 = e.getY();
         } else {
            JOptionPane.showMessageDialog(null,"Drawing Error!!\n You must draw within the drawing area.","Error Message",JOptionPane.ERROR_MESSAGE);
         }
      }

      public void mouseDragged (MouseEvent e) {
         if ((e.getX()>=50&&e.getX()<=750)&&(e.getY()>=100&&e.getY()<=525)) {
            x2 = e.getX();
            y2 = e.getY();
            cord2.setText("Dimensions: W > "+(Math.abs(x2-x1))+" H > "+(Math.abs(y2-y1)));
            if (mode == 6) {
               lx1=lx2;
               ly1=ly2;
               lx2 = x2;
               ly2 = y2;
               flV.add(new coord(lx1,ly1,lx2,ly2,c));
            }
            repaint();
         }
      }

      public void mouseMoved (MouseEvent e) {
         if ((e.getX()>=50&&e.getX()<=750)&&(e.getY()>=100&&e.getY()<=525)) {
            cord1.setText("Coordinates: ("+(e.getX()-50)+","+(e.getY()-100)+")");   
         }
      }

      public void mouseReleased (MouseEvent e) {
         if ((e.getX()>=50&&e.getX()<=750)&&(e.getY()>=100&&e.getY()<=525)) {
            if (mode == 1) {
               lV.add(new coord(x1,y1,e.getX(),e.getY(),c));
            }
            if (mode == 2) {
               oV.add(new coord(x1,y1,e.getX(),e.getY(),c));
            }
            if (mode == 3) {
               foV.add(new coord(x1,y1,e.getX(),e.getY(),c));
            }
            if (mode == 4) {
               rV.add(new coord(x1,y1,e.getX(),e.getY(),c));
            }
            if (mode == 5) {
               frV.add(new coord(x1,y1,e.getX(),e.getY(),c));
            }
            x1=lx1=x2=lx2=50;
            y1=ly1=y2=ly2=100;
         }
      }

   }

   public void update (Graphics g) {
      g.setColor(bgc);
      g.fillRect(53,103,697,422);
      paint(g);
   }

   public void paint (Graphics g) {
      g.setColor(Color.black);
      g.drawLine(0,42,800,42);
      g.drawRect(50,100,700,425);
      for (int i=0;i<oV.size();i++) {
         g.setColor(((coord)oV.elementAt(i)).colour());
         g.drawOval(((coord)oV.elementAt(i)).X1(),((coord)oV.elementAt(i)).Y1(),((coord)oV.elementAt(i)).X2()-((coord)oV.elementAt(i)).X1(),((coord)oV.elementAt(i)).Y2()-((coord)oV.elementAt(i)).Y1());
      }
      for (int i=0;i<foV.size();i++) {
         g.setColor(((coord)foV.elementAt(i)).colour());
         g.fillOval(((coord)foV.elementAt(i)).X1(),((coord)foV.elementAt(i)).Y1(),((coord)foV.elementAt(i)).X2()-((coord)foV.elementAt(i)).X1(),((coord)foV.elementAt(i)).Y2()-((coord)foV.elementAt(i)).Y1());
      }
      for (int i=0;i<rV.size();i++) {
         g.setColor(((coord)rV.elementAt(i)).colour());
         g.drawRect(((coord)rV.elementAt(i)).X1(),((coord)rV.elementAt(i)).Y1(),((coord)rV.elementAt(i)).X2()-((coord)rV.elementAt(i)).X1(),((coord)rV.elementAt(i)).Y2()-((coord)rV.elementAt(i)).Y1());
      }
      for (int i=0;i<frV.size();i++) {
         g.setColor(((coord)frV.elementAt(i)).colour());
         g.fillRect(((coord)frV.elementAt(i)).X1(),((coord)frV.elementAt(i)).Y1(),((coord)frV.elementAt(i)).X2()-((coord)frV.elementAt(i)).X1(),((coord)frV.elementAt(i)).Y2()-((coord)frV.elementAt(i)).Y1());
      }
      for (int i=0;i<lV.size();i++) {
         g.setColor(((coord)lV.elementAt(i)).colour());
         g.drawLine(((coord)lV.elementAt(i)).X1(),((coord)lV.elementAt(i)).Y1(),((coord)lV.elementAt(i)).X2(),((coord)lV.elementAt(i)).Y2());
      }
      for (int i=0;i<flV.size();i++) {
         g.setColor(((coord)flV.elementAt(i)).colour());
         g.drawLine(((coord)flV.elementAt(i)).X1(),((coord)flV.elementAt(i)).Y1(),((coord)flV.elementAt(i)).X2(),((coord)flV.elementAt(i)).Y2());
      }
      g.setColor(c);
      if (mode == 1) {
         g.drawLine(x1,y1,x2,y2);
      }
      if (mode == 2) {
         g.drawOval (x1,y1,x2-x1,y2-y1);
      }                   
      if (mode == 3) {
         g.fillOval (x1,y1,x2-x1,y2-y1);
      }
      if (mode == 4) {
         g.drawRect (x1,y1,x2-x1,y2-y1);
      }
      if (mode == 5) {
         g.fillRect (x1,y1,x2-x1,y2-y1);
      }
      if (mode == 6) {
         g.drawLine(lx1,ly1,lx2,ly2);
      }
   }

   class coord implements Serializable {

      public int x1,y1,x2,y2;
      public Color clr;

      public coord (int a,int b,int c, int d, Color cr) {
         x1 = a;
         y1 = b;
         x2 = c;
         y2 = d;
         clr = cr;
      }

      public Color colour () {
         return clr;
      }

      public int X1 () {
         return x1;
      }

      public int X2 () {
         return x2;
      }

      public int Y1 () {
         return y1;
      }

      public int Y2 () {
         return y2;
      }

   }

}
