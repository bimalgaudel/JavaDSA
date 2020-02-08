import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the TableEntry class.
 */
public class TableEntryTest extends TestCase {
  private TableEntry anEntry;

  public void setUp() {
    anEntry = new TableEntry(new Record("Poochie"));
  }

  public void testConstructors() {
    TableEntry sampleEntry = new TableEntry();
    assertNotNull(sampleEntry);
  }

  public void testGetRecord() {
	Record gotRecord = anEntry.getRecord();
    assertEquals(gotRecord.getName(), "Poochie");
  }

  public void testIsActive() {
    assertEquals(anEntry.isActive(), true);
  }

  public void testDeletion() {
    anEntry.markDeleted();
    assertEquals(anEntry.isDeleted(), true);
  }

  public void testIsFree() {
    TableEntry sampleEntry = new TableEntry();
    assertEquals(sampleEntry.isFree(), true);
  }

  public void testGetRecordName() {
    assertEquals(anEntry.getRecordName(), anEntry.getRecord().getName());
  }

  // public void
}
