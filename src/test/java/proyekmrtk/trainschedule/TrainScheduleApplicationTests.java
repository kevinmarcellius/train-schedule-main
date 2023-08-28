package proyekmrtk.trainschedule;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TrainScheduleApplicationTests {
	@Test
	void contextLoads() {
		TrainScheduleApplication.main(new String[]{});
		assertTrue(true, "App test");
	}
}
