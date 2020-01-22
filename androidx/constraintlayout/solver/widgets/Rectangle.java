package androidx.constraintlayout.solver.widgets;

public class Rectangle
{
  public int height;
  public int width;
  public int x;
  public int y;
  
  public Rectangle() {}
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    int i = x;
    if ((paramInt1 >= i) && (paramInt1 < i + width))
    {
      paramInt1 = y;
      if ((paramInt2 >= paramInt1) && (paramInt2 < paramInt1 + height)) {
        return true;
      }
    }
    return false;
  }
  
  public int getCenterX()
  {
    return (x + width) / 2;
  }
  
  public int getCenterY()
  {
    return (y + height) / 2;
  }
  
  void grow(int paramInt1, int paramInt2)
  {
    x -= paramInt1;
    y -= paramInt2;
    width += paramInt1 * 2;
    height += paramInt2 * 2;
  }
  
  boolean intersects(Rectangle paramRectangle)
  {
    int i = x;
    int j = x;
    if ((i >= j) && (i < j + width))
    {
      i = y;
      j = y;
      if ((i >= j) && (i < j + height)) {
        return true;
      }
    }
    return false;
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    x = paramInt1;
    y = paramInt2;
    width = paramInt3;
    height = paramInt4;
  }
}
