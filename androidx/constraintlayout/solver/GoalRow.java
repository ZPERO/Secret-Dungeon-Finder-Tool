package androidx.constraintlayout.solver;

public class GoalRow
  extends ArrayRow
{
  public GoalRow(Cache paramCache)
  {
    super(paramCache);
  }
  
  public void addError(SolverVariable paramSolverVariable)
  {
    super.addError(paramSolverVariable);
    usageInRowCount -= 1;
  }
}
