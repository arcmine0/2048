import java.awt.*;

public class Card {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private int x=0;
    private int y=0;
    private int w=120;
    private int h=120;
    private int i=0;
    private int j=0;

    private int start=25;
    private int num=0;
    private boolean merge=false;

    public Card(int i,int j){
        this.i=i;
        this.j=j;
        cal();
    }

    public void cal(){
        this.x=start+j*w+(j+1)*10;
        this.y=start+i*h+(i+1)*10;
    }
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

    private static int getWordWidth(Font font,String content,Graphics g) {
        FontMetrics metrics = g.getFontMetrics(font);
        int width=0;
        for(int i=0;i<content.length();i++){
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

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

    public void setNum(int num) {
        this.num=num;
    }

    public int getNum() {
        return this.num;
    }

    public boolean moveTop(Card[][] cards) {
        boolean flag = false;
        if(i==0){
            return false;
        }
        Card prev = cards[i-1][j];
        if(prev.getNum()==0){
            prev.num=this.num;
            this.num=0;
            flag=true;
            prev.moveTop(cards);
        }else if(prev.getNum()==this.num && !this.merge){
            prev.merge=true;
            prev.num=prev.num+this.num;
            this.num=0;
            flag=true;
            prev.moveTop(cards);
        }
        return flag;
    }

    public void setMerge(boolean b) {
        this.merge=b;
    }

    public boolean moveLeft(Card[][] cards) {
        boolean flag=false;
        if(j==0){
            return false;
        }
        Card prev = cards[i][j-1];
        if(prev.getNum()==0){
            prev.num=this.num;
            this.num=0;
            flag=true;
            prev.moveLeft(cards);
        }else if(prev.getNum()==this.num && !this.merge){
            prev.merge=true;
            prev.num=prev.num+this.num;
            this.num=0;
            flag=true;
            prev.moveLeft(cards);
        }
        return flag;
    }

    public boolean moveDown(Card[][] cards) {
        boolean flag=false;
        if(i==ROWS-1){
            return false;
        }
        Card prev = cards[i+1][j];
        if(prev.getNum()==0){
            prev.num=this.num;
            this.num=0;
            flag=true;
            prev.moveDown(cards);
        }else if(prev.getNum()==this.num && !this.merge){
            prev.merge=true;
            prev.num=prev.num+this.num;
            this.num=0;
            flag=true;
            prev.moveDown(cards);
        }
        return flag;
    }

    public boolean moveRight(Card[][] cards) {
        boolean flag=false;
        if(j==COLS-1){
            return false;
        }
        Card prev = cards[i][j+1];
        if(prev.getNum()==0){
            prev.num=this.num;
            this.num=0;
            flag=true;
            prev.moveRight(cards);
        }else if(prev.getNum()==this.num && !this.merge){
            prev.merge=true;
            prev.num=prev.num+this.num;
            this.num=0;
            flag=true;
            prev.moveRight(cards);
        }
        return flag;
    }

}
