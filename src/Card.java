import java.awt.*;

public class Card {
    private static final int ROWS = 4;  //行卡片数
    private static final int COLS = 4;  //列卡片数
    private int x=0;  //卡片横坐标
    private int y=0;  //卡片纵坐标

    private int w=120;  //卡片宽
    private int h=120;  //卡片高
    private int i=0;  //卡片行位置
    private int j=0;  //卡片列位置

    private int start=25;  //卡片与边框距离
    private int num=0;  //卡片数值
    private boolean merge=false;  //卡片是否合并状态

    public Card(int i,int j){
        this.i=i;
        this.j=j;
        cal();
    }

    //计算卡片横纵坐标
    public void cal(){
        this.x=start+j*w+(j+1)*10;
        this.y=start+i*h+(i+1)*10;
    }

    //设置卡片数值
    public void setNum(int num) {
        this.num=num;
    }

    //获得卡片数值
    public int getNum() {
        return this.num;
    }

    //设置卡片合并状态
    public void setMerge(boolean b) {
        this.merge=b;
    }

    //卡片绘制
    public void draw(Graphics g) {
        Color color=getColor();
        Color oColor=g.getColor();
        g.setColor(color);
        g.fillRoundRect(x,y,w,h,4,4);

        if(num!=0){
            g.setColor(new Color(125,78,51));
            Font font = new Font("思源宋体",Font.BOLD,50);
            g.setFont(font);
            String text = num+"";
            int textLen = getWordWidth(font,text,g);
            int tx = x+(w-textLen)/2;
            int ty = y+78;
            g.drawString(text,tx,ty);
        }

        g.setColor(oColor);
    }

    //计算数字长度
    private static int getWordWidth(Font font,String content,Graphics g) {
        FontMetrics metrics = g.getFontMetrics(font);
        int width=0;
        for(int i=0;i<content.length();i++){
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    //获得卡片颜色
    private Color getColor(){
        Color color=null;
        switch(num){
            case 2:
                color=new Color(238, 244, 234);
                break;
            case 4:
                color=new Color(222, 236, 200);
                break;
            case 8:
                color=new Color(174,213,130);
                break;
            case 16:
                color=new Color(142,201,75);
                break;
            case 32:
                color=new Color(111,148,48);
                break;
            case 64:
                color=new Color(76,174,124);
                break;
            case 128:
                color=new Color(60,180,144);
                break;
            case 256:
                color=new Color(45,130,120);
                break;
            case 512:
                color=new Color(9,97,26);
                break;
            case 1024:
                color=new Color(242,177,121);
                break;
            case 2048:
                color=new Color(223,185,0);
                break;

            default:
                color=new Color(92,151,117);
                break;
        }
        return color;
    }

    //卡片移动
    public boolean moveTop(Card[][] cards,boolean b) {
        if(i==0){
            return false;
        }
        Card prev = cards[i-1][j];
        if(prev.getNum()==0){
            if(b){
                prev.num=this.num;
                this.num=0;
                prev.moveTop(cards,b);
            }
            return true;
        }else if(prev.getNum()==this.num && !this.merge){
            if(b){
                prev.merge=true;
                prev.num=prev.num+this.num;
                this.num=0;
                prev.moveTop(cards,b);
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean moveDown(Card[][] cards,boolean b) {
        if(i==ROWS-1){
            return false;
        }
        Card prev = cards[i+1][j];
        if(prev.getNum()==0){
            if(b){
                prev.num=this.num;
                this.num=0;
                prev.moveDown(cards,b);
            }
            return true;
        }else if(prev.getNum()==this.num && !this.merge){
            if(b){
                prev.merge=true;
                prev.num=prev.num+this.num;
                this.num=0;
                prev.moveDown(cards,b);
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean moveLeft(Card[][] cards,boolean b) {
        if(j==0){
            return false;
        }
        Card prev = cards[i][j-1];
        if(prev.getNum()==0){
            if(b){
                prev.num=this.num;
                this.num=0;
                prev.moveLeft(cards,b);
            }
            return true;
        }else if(prev.getNum()==this.num && !this.merge){
            if(b){
                prev.merge=true;
                prev.num=prev.num+this.num;
                this.num=0;
                prev.moveLeft(cards,b);
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean moveRight(Card[][] cards,boolean b) {
        if (j == COLS - 1) {
            return false;
        }
        Card prev = cards[i][j + 1];
        if (prev.getNum() == 0) {
            if (b) {
                prev.num = this.num;
                this.num = 0;
                prev.moveRight(cards, b);
            }
            return true;
        } else if (prev.getNum() == this.num && !this.merge) {
            if (b) {
                prev.merge = true;
                prev.num = prev.num + this.num;
                this.num = 0;
                prev.moveRight(cards, b);
            }
            return true;
        } else {
            return false;
        }
    }
}
