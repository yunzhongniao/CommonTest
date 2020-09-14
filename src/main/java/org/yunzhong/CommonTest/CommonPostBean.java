package org.yunzhong.CommonTest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CommonPostBean {
	public static final String lockKey = "asdf";

	public static long num = 1;

//	@PostConstruct
	public void postConstruct() {
		while (true) {
			for (int i = 0; i < 10; i++) {

				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						List<TestModel> models = new ArrayList<>();
						for (int i = 0; i < 100; i++) {
							synchronized (lockKey) {
								TestModel testModel = new TestModel();
								testModel.setValue("name", String.valueOf(num++),
										String.valueOf(System.currentTimeMillis()));
								try {
									testModel.setValueRandom(testModel.getName(), testModel.getId(),
											testModel.getTimestamp());
								} catch (Exception e) {
									e.printStackTrace();
								}
								models.add(testModel);
							}
						}
						System.out.println("current total count :" + num);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				new Thread(runnable).start();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
