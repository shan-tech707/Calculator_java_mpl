import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Calculator{
    // GUI Component declaration
    JFrame frame=new JFrame("Calculator");
    static JTextField txt_Operation=new JTextField(15);
    JButton btn_1=new JButton("1");
    JButton btn_2=new JButton("2");
    JButton btn_3=new JButton("3");
    JButton btn_4=new JButton("4");
    JButton btn_5=new JButton("5");
    JButton btn_6=new JButton("6");
    JButton btn_7=new JButton("7");
    JButton btn_8=new JButton("8");
    JButton btn_9=new JButton("9");
    JButton btn_0=new JButton("0");
    JButton btn_div=new JButton("/");
    JButton btn_mul=new JButton("*");
    JButton btn_sub=new JButton("-");
    JButton btn_add=new JButton("+");
    JButton btn_dec=new JButton(".");
    JButton btn_eq=new JButton("=");
    JButton btn_del=new JButton("Delete");
    JButton btn_clr=new JButton("Clear");
    final Color defaultBackground = btn_0.getBackground();
    final Color enteredBackground = new Color(243, 245, 228);

    private static final String[] buttonNames = {"btn_7", "btn_8", "btn_9", "btn_div",
                                                 "btn_4","btn_5", "btn_6", "btn_mul",
                                                 "btn_1", "btn_2", "btn_3", "btn_sub",
                                                 "btn_dec", "btn_0", "btn_eq", "btn_add",
                                                        "btn_del","btn_clr"};

    Calculator() {
        init();
        mouseEvent();
        keyboardEvent();
    }

    private void init(){
        JPanel p1=new JPanel();
        p1.setLayout(new GridLayout(4,4,10,10));
        p1.add(btn_7);
        p1.add(btn_8);
        p1.add(btn_9);
        p1.add(btn_div);
        p1.add(btn_4);
        p1.add(btn_5);
        p1.add(btn_6);
        p1.add(btn_mul);
        p1.add(btn_1);
        p1.add(btn_2);
        p1.add(btn_3);
        p1.add(btn_sub);
        p1.add(btn_dec);
        p1.add(btn_0);
        p1.add(btn_eq);
        p1.add(btn_add);
        JPanel p2=new JPanel();
        p2.setLayout(new GridLayout(1,2,10,10));
        p2.add(btn_del);
        p2.add(btn_clr);

        GridBagLayout lay_out=new GridBagLayout();
        frame.setLayout(lay_out);
        Container container= frame.getContentPane();
        container.setLayout(lay_out);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(5,5,5,5);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=4;
        gbc.ipady=10;
        frame.add(txt_Operation,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        frame.add(p1,gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=2;
        frame.add(p2,gbc);
        frame.pack();
        frame.setVisible(true);
    }

    private void mouseEvent(){
        try {
            final Method addEventListenerMethod = Class.forName("javax.swing.JButton").getMethod("addMouseListener", MouseListener.class);
            for (String buttonName : buttonNames) {
                final Field buttonField = getClass().getDeclaredField(buttonName);
                buttonField.setAccessible(true);

                addEventListenerMethod.invoke(buttonField.get(this), new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        txt_Operation.requestFocus();
                        JButton button = (JButton) e.getSource();
                        if(button.equals(btn_eq)) {
                            setResult();
                        }
                        else if(button.equals(btn_del)) {
                            String s = txt_Operation.getText();
                            if(!s.isEmpty())
                            {
                                txt_Operation.setText(s.substring(0, s.length()-1));
                            }
                        }
                        else if(button.equals(btn_clr)) {
                            txt_Operation.setText("");
                        }
                        else {
                            char c = button.getText().charAt(0);
                            String boxText = txt_Operation.getText();
                            if (ExpressionHandler.canInput(boxText, c))
                                txt_Operation.setText(boxText + c);
                            else
                                showMessage("Please click on legal characters!");
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {}

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ((JButton)e.getSource()).setBackground(enteredBackground);
                        ((JButton)e.getSource()).setBackground(enteredBackground);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((JButton) e.getSource()).setBackground(defaultBackground);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error: "+e.getMessage());
            frame.dispose();
        }
    }

    private void keyboardEvent(){
        txt_Operation.addKeyListener(new KeyListener() {
            private final Map<Integer,JButton> map = new HashMap<>();{
                map.put(KeyEvent.VK_0,btn_0);
                map.put(KeyEvent.VK_1,btn_1);
                map.put(KeyEvent.VK_2,btn_2);
                map.put(KeyEvent.VK_3,btn_3);
                map.put(KeyEvent.VK_4,btn_4);
                map.put(KeyEvent.VK_5,btn_5);
                map.put(KeyEvent.VK_6,btn_6);
                map.put(KeyEvent.VK_7,btn_7);
                map.put(KeyEvent.VK_8,btn_8);
                map.put(KeyEvent.VK_9,btn_9);
                map.put(KeyEvent.VK_PLUS,btn_add);
                map.put(KeyEvent.VK_MINUS,btn_sub);
                map.put(KeyEvent.VK_MULTIPLY,btn_mul);
                map.put(KeyEvent.VK_SLASH,btn_div);
                map.put(KeyEvent.VK_ENTER,btn_eq);
                map.put(KeyEvent.VK_PERIOD,btn_dec);
                map.put(KeyEvent.VK_ESCAPE,btn_clr);
                map.put(KeyEvent.VK_BACK_SPACE,btn_del);
            }
            @Override
            public void keyTyped(KeyEvent keyEvent) {}

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int code = keyEvent.getKeyCode();
                if(keyEvent.isShiftDown())
                {
                    if(code == KeyEvent.VK_8)
                        btn_mul.setBackground(enteredBackground);
                    else if(code == KeyEvent.VK_EQUALS)
                        btn_add.setBackground(enteredBackground);
                }
                else if(map.containsKey(code))
                {
                    map.get(code).setBackground(enteredBackground);
                    if (code == KeyEvent.VK_ENTER)
                    {
                        setResult();
                    }
                    else if(code == KeyEvent.VK_ESCAPE)
                    {
                        txt_Operation.setText("");
                    }
                    else if(code == KeyEvent.VK_EQUALS)
                    {
                        setResult();
                    }
                    else
                    {
                        map.get(keyEvent.getKeyCode()).setBackground(enteredBackground);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                map.forEach((k,v)->v.setBackground(defaultBackground));
            }
        });
    }

    private void setResult() {
        if (!txt_Operation.getText().isEmpty()){
            ExpressionHandler.setExpression(txt_Operation.getText());
            try {
                showMessage(ExpressionHandler.valid() ? ExpressionHandler.result() :"Expression error, please re-enter");
            } catch (Exception e) {
                showMessage("The divisor is 0, please re-enter");
            }
            //txt_Operation.setText("");
        } else {
            showMessage("The expression is empty, please re-enter");
        }
    }

    private void showMessage(String message) {
        txt_Operation.setText(""+message);
    }

    public static void main(String...s)
    {
        new Calculator();
    }

}
