import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestSuite {

	public static void main(String args[]) {

		Result result = JUnitCore.runClasses(BagTest.class, SwordTest.class, PotionTest.class, BombTest.class,
				TreasureTest.class, EnemyTest.class, GoalTest.class, PlayerTest.class, SwitchTest.class, DoorTest.class,
				BoulderTest.class, BomberTest.class, GoblinTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}

}
