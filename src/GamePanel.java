import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int ROWS = 4;  //行卡片数
    private static final int COLS = 4;  //列卡片数
    private JFrame frame = null;  //panel面板所属窗口
    private Card[][] cards = new Card[ROWS][COLS];  //卡片所在二维数组
    private String gameFlag = "start";  //游戏状态

    public GamePanel(JFrame frame) {
        this.setLayout(new FlowLayout());  //设置组件布局
        this.setOpaque(false);  //设置组件透明度
        this.frame=frame;

        createMenu();  //创建菜单
        initCard();  //初始化卡片
        createRandomNum();  //卡片随即赋值
        creatKeyListener();  //设置监听器
    }


    //菜单创建--------------------------------------------------------------------------------

    //设置字体
    private Font createFont() {
        return new Font("思源宋体",Font.BOLD,20);
    }

    //创建菜单栏以及按钮监听
    private void createMenu() {
        Font tFont = createFont();

        JMenuBar jmb = new JMenuBar();

        JMenu jMenu1 = new JMenu("游戏");
        jMenu1.setFont(tFont);
        JMenuItem jmi1 = new JMenuItem("新游戏");
        jmi1.setFont(tFont);
        JMenuItem jmi2 = new JMenuItem("退出");
        jmi2.setFont(tFont);
        jMenu1.add(jmi1);
        jMenu1.add(jmi2);

        JMenu jMenu2 = new JMenu("帮助");
        jMenu2.setFont(tFont);
        JMenuItem jmi3 = new JMenuItem("操作帮助");
        jmi3.setFont(tFont);
        JMenuItem jmi4 = new JMenuItem("胜利条件");
        jmi4.setFont(tFont);
        jMenu2.add(jmi3);
        jMenu2.add(jmi4);

        jmb.add(jMenu1);
        jmb.add(jMenu2);

        frame.setJMenuBar(jmb);

        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi4.addActionListener(this);

        jmi1.setActionCommand("restart");
        jmi2.setActionCommand("exit");
        jmi3.setActionCommand("help");
        jmi4.setActionCommand("win");
    }

    //按钮功能实现
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("restart".equals(command)){
            restart();
        }else if("exit".equals(command)){
            Object[] options={"确定","取消"};
            int res = JOptionPane.showOptionDialog(this,"你确定要退出游戏吗？","",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if(res==0){
                System.exit(0);
            }
        }else if("help".equals(command)){
            JOptionPane.showMessageDialog(null,"通过键盘的wasd或上下左右来移动，相同数字会合并","操作帮助",JOptionPane.INFORMATION_MESSAGE);
        }else if("win".equals(command)){
            JOptionPane.showMessageDialog(null,"得到数字2048获得胜利，当没有空卡片且不能进行合并时失败","胜利条件",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void restart() {
        gameFlag="start";
        initCard();
        createRandomNum();
        repaint();
    }

    //卡片绘制---------------------------------------------------------------------------

    //初始化所有卡片
    private void initCard() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = new Card(i,j);
                cards[i][j]=card;
            }
        }
    }

    //判断卡片是否已满
    private boolean cardIsFull() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++) {
                card = cards[i][j];
                if(card.getNum()==0){
                    return false;
                }
            }
        }
        return true;
    }

    //卡片随机赋值2或4
    private void createRandomNum() {
        int num=0;
        Random random = new Random();
        int n = random.nextInt(5) + 1;
        if(n==1){
            num=4;
        }else{
            num=2;
        }

        if(cardIsFull()){
            return;
        }

        Card card=getRandomCard(random);
        if(card!=null){
            card.setNum(num);
        }
    }

    //获取随机一张卡片
    private Card getRandomCard(Random random) {
        int i = random.nextInt(ROWS);
        int j = random.nextInt(COLS);
        Card card = cards[i][j];

        if(card.getNum()==0){
            return card;
        }
        return getRandomCard(random);
    }

    //绘制
    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawCard(g);
    }

    private void drawCard(Graphics g) {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                card.draw(g);
            }
        }
    }

    //键盘输入监听-------------------------------------------------------------------------

    //设置监听器
    private void creatKeyListener() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!"start".equals(gameFlag)){
                    return;
                }
                int key = e.getKeyCode();
                switch (key){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        moveCard(1);
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        moveCard(2);
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        moveCard(3);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        moveCard(4);
                        break;
                }
            }
        };
        frame.addKeyListener(keyAdapter);
    }

    //根据按键执行对应操作
    private void moveCard(int dir) {
        clearCard();

        if(dir==1){
            moveCardTop(true);
        }else if(dir==2){
            moveCardRight(true);
        }else if(dir==3){
            moveCardDown(true);
        }else if(dir==4){
            moveCardLeft(true);
        }

        createRandomNum();
        repaint();
        gameOverOrNot();
    }

    //重置卡片合并状态
    private void clearCard() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                card.setMerge(false);
            }
        }
    }

    //像上移动
    private boolean moveCardTop(boolean b) {
        Card card;
        boolean res = false;
        for(int i=1;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                if(card.getNum()!=0){
                    if(card.moveTop(cards,b)){
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    //向下移动
    private boolean moveCardDown(boolean b) {
        Card card;
        boolean res = false;
        for(int i=ROWS-2;i>=0;i--){
            for(int j=0;j<COLS;j++){
                card=cards[i][j];
                if(card.getNum()!=0){
                    if(card.moveDown(cards,b)){
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    //向左移动
    private boolean moveCardLeft(boolean b) {
        Card card;
        boolean res = false;
        for(int j=1;j<COLS;j++){
            for(int i=0;i<ROWS;i++){
                card=cards[i][j];
                if(card.getNum()!=0){
                    if(card.moveLeft(cards,b)){
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    //向右移动
    private boolean moveCardRight(boolean b) {
        Card card;
        boolean res = false;
        for(int j=COLS-2;j>=0;j--){
            for(int i=0;i<ROWS;i++){
                card = cards[i][j];
                if(card.getNum()!=0){
                    if(card.moveRight(cards,b)){
                        res = true;
                    }
                }
            }
        }
        return  res;
    }

    //游戏结束判断-----------------------------------------------------------------------------

    private void gameOverOrNot() {
        if(isWin()){
            gameWin();
        }else if(cardIsFull()){
            if(moveCardLeft(false) || moveCardDown(false) || moveCardRight(false) || moveCardTop(false)){
                return;
            }else {
                gameOver();
            }
        }
    }

    //判断是否胜利
    private boolean isWin() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                if(card.getNum()==2048){
                    return true;
                }
            }
        }
        return false;
    }

    //胜利窗口
    private void gameWin() {
        gameFlag="end";
        UIManager.put("OptionPane.buttonFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        UIManager.put("OptionPane.messageFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        JOptionPane.showMessageDialog(frame,"你成功了，太棒了！");
    }

    //失败窗口
    private void gameOver() {
        gameFlag="end";
        UIManager.put("OptionPane.buttonFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        UIManager.put("OptionPane.messageFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        JOptionPane.showMessageDialog(frame,"你失败了，请再接再厉！");
    }
}