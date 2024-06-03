import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.lang.Math;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Stack;

// kalkulator uzywa pierwszego podanego operatora tzn
// jesli wykonamy nastepujace operacje
// > 2
// > +
// > -
// > 2
// =
// to otrzymamy 4 zamiast 0, pierwszy operator zawsze zostanie wykonany
public class Calculator implements ActionListener, KeyListener
{
    JTextField t1;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b6;
    JButton b7;
    JButton b8;
    JButton b9;
    JButton b0;
    JButton bC;
    JButton bplus,brow,bminus,bdot,bpower,broot,bpercent,bmulti,bdivide,bmem,bprev;
    String currentOperation = "";
    String previousOperation = "";
    String mem = "";
    double x,buf, cached_x;
    long bufint;
    int rewind_counter = 0;
    File memFile = new File("memfile.txt");
    boolean chain = false;
    boolean did_skip = false;
//    boolean chain_op = false;
    boolean prev_op = false;
    boolean clean_t1_after_equals_if_number = false;
    FileWriter fw;

    // operacja prev (cofanie) opiera sie na staku z poprzednimi operandami i operatorami
    Stack memStack = new Stack();

    //  klawiatury wprowadzane moga byc tylko wprowadzane cyfry
    public void keyPressed(KeyEvent e)
    {
        t1.requestFocus();
        if (clean_t1_after_equals_if_number)
        {
            t1.setText("");
        }

        if (!t1.getText().isEmpty())
        {
            bufint=Long.parseLong(t1.getText());
            System.out.println(t1.getText());
            if (Character.isDigit(e.getKeyChar()))
            {
                t1.setText((String.valueOf(bufint) + e.getKeyChar()));
            }
            else
            {
                ;
            }
        }
        else
        {

            if (Character.isDigit(e.getKeyChar()))
            {
                t1.setText(String.valueOf(e.getKeyChar()));
            }
        }
    }

    // metody wprowadzone na potrzeby interfejsu aczkolwiek nie uzyte
    public void keyReleased(KeyEvent e)
    {
        // metoda nie uzyta
    }

    public void keyTyped(KeyEvent e)
    {
        // metoda nie uzyta
    }

    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource();

        if (target == bprev)
        {
            ;
        }
        else
        {
            did_skip = false;

        }

        if (target==b1)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;
            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b2)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;
            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }

            t1.requestFocus();
        }
        else if(target==b3)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;
            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b4)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;

            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b5)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;

            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b6)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;

            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b7)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;

            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b8)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;

            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b9)
        {
            if (clean_t1_after_equals_if_number)
            {
                t1.setText(((JButton)target).getText());
                clean_t1_after_equals_if_number = false;
            }
            else
            {
                t1.setText(t1.getText()+((JButton)target).getText());
            }
            t1.requestFocus();
        }
        else if(target==b0)
        {
            if (clean_t1_after_equals_if_number)
            {

                t1.setText(((JButton) target).getText() + '.');

                clean_t1_after_equals_if_number = false;
            }
            else
            {
                if(t1.getText().isEmpty())
                {
                    t1.setText(t1.getText() + ((JButton) target).getText() + '.');
                }else
                {
                    t1.setText(t1.getText()+((JButton)target).getText());
                    t1.requestFocus();
                }
            }

        }

        if (!t1.getText().isEmpty())
        {
            // zanim zmienimy obecny operator, wykonujemy podmianę bufora jesli nie uzylismy rowna sie
            // tylko inny operator, zabieg przeprowadzony w celu tego zeby kalkulator funkcjonowal jak ten windowsowy
             if (target == bplus) {
                 if(prev_op)
                 {
                     equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "+";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "+";
                }
                prev_op = true;

            } else if (target == bmem) {
                //t1.setText("");
                t1.requestFocus();
                if (mem.isEmpty()) {
                    mem = t1.getText();
                } else {
                    t1.setText(mem);
                    mem = "";
                }
            } else if (target == bminus) {
                 if (prev_op)
                 {
                    equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "-";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "-";
                }
                 prev_op = true;
            } else if (target == bdot) {
                if (t1.getText().isEmpty()) {
                    t1.setText('0' + t1.getText() + ((JButton) target).getText());
                } else {
                    if (!t1.getText().contains(".")) {
                        t1.setText(t1.getText() + ((JButton) target).getText());
                        t1.requestFocus();
                    }
                }

            } else if (target == bpower) {
                 if (prev_op)
                 {
                    equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "^";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "^";
                }
                prev_op = true;
             } else if (target == bC) {
                 t1.setText("");
            } else if (target == bprev) {
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    System.out.println("Brak Historii");
                } else {
                    if (!memStack.isEmpty()) {
                        if (did_skip == false)
                        {
                            rewind_counter = 0;
                            while (rewind_counter != 5)
                            {
                                memStack.pop();
                                rewind_counter += 1;
                            }
                        }
                        did_skip = true;
                        rewind_counter = 0;
                        while (rewind_counter != 5) {
                            switch (rewind_counter) {
                                case 0: {
                                    double pop = (Double) memStack.pop();
                                    System.out.println(pop);
                                    t1.setText(String.valueOf(pop));
                                }
                                break;
                                case 1: {
                                    String pop = (String) memStack.pop();
                                    System.out.println(pop);
                                    currentOperation = pop;
                                }
                                break;
                                case 2: {
                                    Double pop = (Double) memStack.pop();
                                    System.out.println(pop);
                                    x = pop;
                                }

                                break;
                                case 3: {
                                    String pop = (String) memStack.pop();
                                    System.out.println(pop);
                                    previousOperation = pop;
                                }
                                break;
                                case 4:
                                    memStack.pop();
                                    break;


                            }
                            rewind_counter += 1;
                        }

                    } else {
                        System.out.println("Brak dalszej historii.");
                    }

                }
            } else if (target == broot) {
                 if (prev_op)
                 {
                     equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "√";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "√";
                }
                 prev_op = true;
            } else if (target == bpercent) {
                 if (prev_op)
                 {
                     equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "%";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "%";
                }
                prev_op = true;

            } else if (target == bmulti) {
                 if (prev_op)
                 {
                    equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "*";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "*";
                }
                 prev_op = true;
            } else if (target == bdivide) {
                 if (prev_op)
                 {
                    equals();
                 }
                 else
                 {
                     buf = Double.parseDouble(t1.getText());
                 }
                t1.setText("");
                t1.requestFocus();
                if (currentOperation.isEmpty()) {
                    currentOperation = "/";
                } else {
                    previousOperation = currentOperation;
                    currentOperation = "/";
                }
                 prev_op = true;
            } else if (target == brow || target == t1) {
                equals();
                previousOperation = currentOperation;
                currentOperation = "row";
                clean_t1_after_equals_if_number = true;
            }
        }

    }

    void equals()
    {
        // boolean odpowiedzialny za "łączenie operatorów" - jesli wcisniemy operand operator operand operator,
        // to te pierwsze trzy zostana wrzucone w buf, dlatego mozna uzywac operatorow bez rowna sie
        // a wyniki beda sie sumowaly/odejmowaly etc.
        prev_op = false;

        if (currentOperation == "row")
        {
            chain = true;
            currentOperation = previousOperation;
            previousOperation = "row";
        }
        else
        {
            chain = false;
        }

        if (!previousOperation.equals("row") || (previousOperation.equals("row") && chain == false))
        {
            x = Double.parseDouble(t1.getText());
            cached_x = x;
            //System.out.println(cached_x);
        }
        else
        {
            x = cached_x;
            //System.out.println(x);
        }

        switch(currentOperation) {
            case "+":
                try
                {
                    fw.append(String.valueOf(buf));
                    fw.append("+");
                    fw.append(String.valueOf(x));
                    fw.append("=");
                    fw.append(String.valueOf(x + buf));
                    fw.append("\n");
                    fw.flush();
                }
                catch (java.io.IOException exc)
                {
                    System.out.println(exc);
                }
                memStack.push(buf);
                memStack.push("+");
                memStack.push(x);
                buf = buf + x;
                memStack.push("=");
                memStack.push(buf);
                t1.setText(Double.toString(buf));
                t1.requestFocus();
                break;
            case "-":
                try
                {
                    fw.append(String.valueOf(buf));
                    fw.append("-");
                    fw.append(String.valueOf(x));
                    fw.append("=");
                    fw.append(String.valueOf(buf - x));
                    fw.append("\n");
                    fw.flush();
                }
                catch (java.io.IOException exc)
                {
                    System.out.println(exc);
                }
                memStack.push(buf);
                memStack.push("-");
                memStack.push(x);
                buf=buf-x;
                memStack.push("=");
                memStack.push(buf);
                t1.setText(Double.toString(buf));
                t1.requestFocus();
                break;
            case "*":
                memStack.push(x);
                try
                {
                    fw.append(String.valueOf(buf));
                    fw.append("*");
                    fw.append(String.valueOf(x));
                    fw.append("=");
                    fw.append(String.valueOf(x * buf));
                    fw.append("\n");
                    fw.flush();
                }
                catch (java.io.IOException exc)
                {
                    System.out.println(exc);
                }
                memStack.push("*");
                memStack.push(buf);
                buf=buf*x;
                memStack.push("=");
                memStack.push(buf);
                t1.setText(Double.toString(buf));
                t1.requestFocus();
                break;
            case "/":
                if (x == 0)
                {
                    t1.setText("0 division error");
                }
                else
                {
                    try
                    {
                        fw.append(String.valueOf(buf));
                        fw.append("/");
                        fw.append(String.valueOf(x));
                        fw.append("=");
                        fw.append(String.valueOf(buf / x));
                        fw.append("\n");
                        fw.flush();

                    }
                    catch (java.io.IOException exc)
                    {
                        System.out.println(exc);
                    }
                    memStack.push(x);
                    memStack.push("/");
                    memStack.push(buf);
                    buf=buf/x;
                    memStack.push("=");
                    memStack.push(buf);
                    t1.setText(Double.toString(buf));
                }
                t1.requestFocus();
                break;
            case "^":
                try
                {
                    fw.append(String.valueOf(buf));
                    fw.append("^");
                    fw.append(String.valueOf(x));
                    fw.append("=");
                    fw.append(String.valueOf(Math.pow(buf,x)));
                    fw.append("\n");
                    fw.flush();

                }
                catch (java.io.IOException exc)
                {
                    System.out.println(exc);
                }
                memStack.push(buf);
                memStack.push("^");
                memStack.push(x);
                buf=Math.pow(buf,x);
                memStack.push("=");
                memStack.push(buf);
                t1.setText(Double.toString(buf));
                t1.requestFocus();
                break;
            case "√":
                try
                {
                    fw.append(String.valueOf(buf));
                    fw.append("√");
                    fw.append(String.valueOf(x));
                    fw.append("=");
                    fw.append(String.valueOf(Math.pow(buf,1.0 / x)));
                    fw.append("\n");
                    fw.flush();

                }
                catch (java.io.IOException exc)
                {
                    System.out.println(exc);
                }
                memStack.push(buf);
                memStack.push("√");
                memStack.push(x);
                // W wypadku podania liczby ujemnej java wypisze NaN.
                buf=Math.pow(buf, 1.0 / x);
                memStack.push("=");
                memStack.push(buf);
                t1.setText(Double.toString(buf));
                t1.requestFocus();
                break;
            case "%":
                try
                {
                    fw.append(String.valueOf(buf));
                    fw.append("%");
                    fw.append(String.valueOf(x));
                    fw.append("=");
                    fw.append(String.valueOf(buf * x/100));
                    fw.append("\n");
                    fw.flush();

                }
                catch (java.io.IOException exc)
                {
                    System.out.println(exc);
                }
                memStack.push(buf);
                memStack.push("%");
                memStack.push(x);
                buf=buf * x/100;
                memStack.push("=");
                memStack.push(buf);
                t1.setText(Double.toString(buf));
                t1.requestFocus();
                break;
            default:
                break;

        }
    }
    void init()
    {
        // tworzymy file writer na potrzeby 7.5 rejestracji wszystkich wynikow dzialan
        // przy czym plik ktory zostanie utworzony jest usuwany od razu po zakonczeniu dzialania kalkulatora
        try
        {
            fw = new FileWriter(memFile, true);
        }
        catch (java.io.IOException e)
        {
            System.out.println("There has been an IO exception.");
        }



        try
        {
            memFile.createNewFile();
        }
        catch (java.io.IOException e)
        {
            System.out.println("Memfile not created");;
        }

        // usuwanie pliku z pamięcią operacji sesji
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                    fw.close();
                }catch (IOException e)
                {
                    System.out.println(e);
                }

                memFile.delete();
            }
        });


        JFrame f=new JFrame();

        Container c=f.getContentPane();

        GridBagLayout gbl=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        c.setLayout(gbl);



        t1=new JTextField(20);
        t1.addKeyListener(this);
        t1.addActionListener(this);
        t1.setHorizontalAlignment(JTextField.RIGHT);
        t1.setEditable(false);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=20;
        gbc.ipadx=0;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,5);
        gbl.setConstraints(t1,gbc);
        c.add(t1);

        b1=new JButton("1");
        b1.addActionListener(this);
        b1.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b1,gbc);
        c.add(b1);

        b2=new JButton("2");
        b2.addActionListener(this);
        b2.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b2,gbc);
        c.add(b2);

        b3=new JButton("3");
        b3.addActionListener(this);
        b3.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b3,gbc);
        c.add(b3);

        b4=new JButton("4");
        b4.addActionListener(this);
        b4.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b4,gbc);
        c.add(b4);

        b5=new JButton("5");
        b5.addActionListener(this);
        b5.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b5,gbc);
        c.add(b5);

        b6=new JButton("6");
        b6.addActionListener(this);
        b6.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b6,gbc);
        c.add(b6);

        b7=new JButton("7");
        b7.addActionListener(this);
        b7.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b7,gbc);
        c.add(b7);

        b8=new JButton("8");
        b8.addActionListener(this);
        b8.setFocusable(false);
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b8,gbc);
        c.add(b8);

        b9=new JButton("9");
        b9.addActionListener(this);
        b9.setFocusable(false);
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(b9,gbc);
        c.add(b9);

        b0=new JButton("0");
        b0.addActionListener(this);
        b0.setFocusable(false);
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=3;
        gbc.ipadx=2;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(b0,gbc);
        c.add(b0);

        bdot=new JButton(".");
        bdot.addActionListener(this);
        bdot.setFocusable(false);
        bdot.setToolTipText("kropka");
        gbc.gridx=3;
        gbc.gridy=3;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bdot,gbc);
        c.add(bdot);


        bplus=new JButton("+");
        bplus.addActionListener(this);
        bplus.setFocusable(false);
        bplus.setToolTipText("dodawanie");
        gbc.gridx=3;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bplus,gbc);
        c.add(bplus);

        bminus=new JButton("-");
        bminus.addActionListener(this);
        bminus.setFocusable(false);
        bminus.setToolTipText("odejmowanie");
        gbc.gridx=3;
        gbc.gridy=2;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bminus,gbc);
        c.add(bminus);

        bpower=new JButton("^");
        bpower.addActionListener(this);
        bpower.setFocusable(false);
        bpower.setToolTipText("potegowanie");
        gbc.gridx=5;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bpower,gbc);
        c.add(bpower);

        broot=new JButton("√");
        broot.addActionListener(this);
        broot.setFocusable(false);
        broot.setToolTipText("pierwiastkowanie");
        gbc.gridx=5;
        gbc.gridy=2;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(broot,gbc);
        c.add(broot);

        bpercent=new JButton("%");
        bpercent.addActionListener(this);
        bpercent.setFocusable(false);
        bpercent.setToolTipText("procent");
        gbc.gridx=5;
        gbc.gridy=3;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,0);
        gbl.setConstraints(bpercent,gbc);
        c.add(bpercent);

        brow=new JButton("=");
        brow.addActionListener(this);
        brow.setFocusable(false);
        brow.setToolTipText("wykonaj działanie rowna sie");
        gbc.gridx=5;
        gbc.gridy=4;
        gbc.gridwidth=4;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,5);
        gbl.setConstraints(brow,gbc);
        c.add(brow);

        bprev=new JButton("prev");
        bprev.addActionListener(this);
        bprev.setFocusable(false);
        bprev.setToolTipText("poprzenie dzialanie");
        gbc.gridx=3;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(bprev,gbc);
        c.add(bprev);

        bC=new JButton("C");
        bC.addActionListener(this);
        bC.setFocusable(false);
        bC.setToolTipText("wyczyszczenie okna outputu");
        gbc.gridx=4;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,5,0);
        gbl.setConstraints(bC,gbc);
        c.add(bC);

        bmulti=new JButton("*");
        bmulti.addActionListener(this);
        bmulti.setFocusable(false);
        bmulti.setToolTipText("mnozenie");
        gbc.gridx=7;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,5);
        gbl.setConstraints(bmulti,gbc);
        c.add(bmulti);

        bdivide=new JButton("/");
        bdivide.addActionListener(this);
        bdivide.setFocusable(false);
        bdivide.setToolTipText("dzielenie");
        gbc.gridx=7;
        gbc.gridy=2;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,5);
        gbl.setConstraints(bdivide,gbc);
        c.add(bdivide);

        bmem=new JButton("M");
        bmem.addActionListener(this);
        bmem.setFocusable(false);
        bmem.setToolTipText("pamiec");
        gbc.gridx=7;
        gbc.gridy=3;
        gbc.gridwidth=2;
        gbc.ipadx=30;
        gbc.ipady=0;
        gbc.insets=new Insets(5,5,0,5);
        gbl.setConstraints(bmem,gbc);
        c.add(bmem);



        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Kalk");
        f.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Calculator().init();
            }
        });
    }
}
