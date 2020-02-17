package society.person.dataobjects;

public class SocietyOpinion {
  public int societyId;
  public float societyOpinion;

  public SocietyOpinion(int societyId) {
    this.societyId = societyId;
  }

  public int getSocietyId() {
    return societyId;
  }

  public void setSocietyId(int societyId) {
    this.societyId = societyId;
  }

  public float getSocietyOpinion() {
    return societyOpinion;
  }

  public void setSocietyOpinion(float societyOpinion) {
    this.societyOpinion = societyOpinion;
  }
}
