import java.awt.*;

public class Card {
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
        g.setColor(oColor);
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
}
