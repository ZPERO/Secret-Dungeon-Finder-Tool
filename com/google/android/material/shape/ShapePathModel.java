package com.google.android.material.shape;

public class ShapePathModel
{
  private static final CornerTreatment DEFAULT_CORNER_TREATMENT = new CornerTreatment();
  private static final EdgeTreatment DEFAULT_EDGE_TREATMENT = new EdgeTreatment();
  private EdgeTreatment bottomEdge;
  private CornerTreatment bottomLeftCorner;
  private CornerTreatment bottomRightCorner;
  private EdgeTreatment leftEdge;
  private EdgeTreatment rightEdge;
  private EdgeTreatment topEdge;
  private CornerTreatment topLeftCorner;
  private CornerTreatment topRightCorner;
  
  public ShapePathModel()
  {
    Object localObject = DEFAULT_CORNER_TREATMENT;
    topLeftCorner = ((CornerTreatment)localObject);
    topRightCorner = ((CornerTreatment)localObject);
    bottomRightCorner = ((CornerTreatment)localObject);
    bottomLeftCorner = ((CornerTreatment)localObject);
    localObject = DEFAULT_EDGE_TREATMENT;
    topEdge = ((EdgeTreatment)localObject);
    rightEdge = ((EdgeTreatment)localObject);
    bottomEdge = ((EdgeTreatment)localObject);
    leftEdge = ((EdgeTreatment)localObject);
  }
  
  public EdgeTreatment getBottomEdge()
  {
    return bottomEdge;
  }
  
  public CornerTreatment getBottomLeftCorner()
  {
    return bottomLeftCorner;
  }
  
  public CornerTreatment getBottomRightCorner()
  {
    return bottomRightCorner;
  }
  
  public EdgeTreatment getLeftEdge()
  {
    return leftEdge;
  }
  
  public EdgeTreatment getRightEdge()
  {
    return rightEdge;
  }
  
  public EdgeTreatment getTopEdge()
  {
    return topEdge;
  }
  
  public CornerTreatment getTopLeftCorner()
  {
    return topLeftCorner;
  }
  
  public CornerTreatment getTopRightCorner()
  {
    return topRightCorner;
  }
  
  public void setAllCorners(CornerTreatment paramCornerTreatment)
  {
    topLeftCorner = paramCornerTreatment;
    topRightCorner = paramCornerTreatment;
    bottomRightCorner = paramCornerTreatment;
    bottomLeftCorner = paramCornerTreatment;
  }
  
  public void setAllEdges(EdgeTreatment paramEdgeTreatment)
  {
    leftEdge = paramEdgeTreatment;
    topEdge = paramEdgeTreatment;
    rightEdge = paramEdgeTreatment;
    bottomEdge = paramEdgeTreatment;
  }
  
  public void setBottomEdge(EdgeTreatment paramEdgeTreatment)
  {
    bottomEdge = paramEdgeTreatment;
  }
  
  public void setBottomLeftCorner(CornerTreatment paramCornerTreatment)
  {
    bottomLeftCorner = paramCornerTreatment;
  }
  
  public void setBottomRightCorner(CornerTreatment paramCornerTreatment)
  {
    bottomRightCorner = paramCornerTreatment;
  }
  
  public void setCornerTreatments(CornerTreatment paramCornerTreatment1, CornerTreatment paramCornerTreatment2, CornerTreatment paramCornerTreatment3, CornerTreatment paramCornerTreatment4)
  {
    topLeftCorner = paramCornerTreatment1;
    topRightCorner = paramCornerTreatment2;
    bottomRightCorner = paramCornerTreatment3;
    bottomLeftCorner = paramCornerTreatment4;
  }
  
  public void setEdgeTreatments(EdgeTreatment paramEdgeTreatment1, EdgeTreatment paramEdgeTreatment2, EdgeTreatment paramEdgeTreatment3, EdgeTreatment paramEdgeTreatment4)
  {
    leftEdge = paramEdgeTreatment1;
    topEdge = paramEdgeTreatment2;
    rightEdge = paramEdgeTreatment3;
    bottomEdge = paramEdgeTreatment4;
  }
  
  public void setLeftEdge(EdgeTreatment paramEdgeTreatment)
  {
    leftEdge = paramEdgeTreatment;
  }
  
  public void setRightEdge(EdgeTreatment paramEdgeTreatment)
  {
    rightEdge = paramEdgeTreatment;
  }
  
  public void setTopEdge(EdgeTreatment paramEdgeTreatment)
  {
    topEdge = paramEdgeTreatment;
  }
  
  public void setTopLeftCorner(CornerTreatment paramCornerTreatment)
  {
    topLeftCorner = paramCornerTreatment;
  }
  
  public void setTopRightCorner(CornerTreatment paramCornerTreatment)
  {
    topRightCorner = paramCornerTreatment;
  }
}
