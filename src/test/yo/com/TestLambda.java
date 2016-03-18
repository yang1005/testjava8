package test.yo.com;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestLambda {

	public static void main(String[] args) throws IOException {

		List<String> cities = new ArrayList<String>();
		cities.add("京都");
		cities.add("大阪");
		cities.add("神戸");
		// cities.forEach((city) -> System.out.println(city));
		// 既存のメソッドを(::)で呼び出すことでラムダ式化が出来る。
		cities.forEach(System.out::println);

		int sum = IntStream.range(1, 101).sum();
		System.out.println(sum);

		Path path = FileSystems.getDefault().getPath("src/wordcount.txt");

		long start = System.currentTimeMillis();
		Files.lines(path).flatMap(line -> Arrays.stream(line.split("( |\\.|,)+")))// Map
				.collect(Collectors.groupingBy(word -> word, Collectors.counting()))// Reduce
				.forEach((k, v) -> System.out.println(k + ":" + v));// Output
		System.out.println("並行処理なし処理時間：" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		Files.lines(path)
		// Map
				.flatMap(line -> Arrays.stream(line.split("( |\\.|,)+")))
				// Concurrent
				.parallel()
				// Reduce
				.collect(Collectors.groupingBy(word -> word, Collectors.counting()))
				// Output
				.forEach((k, v) -> System.out.println(k + ":" + v));
		System.out.println("並行処理あり処理時間：" + (System.currentTimeMillis() - start));

		List<String> list = Arrays.asList("C", "C++", "Java", "Scala", "Ruby");
		long count = list.stream().filter(s -> s.startsWith("C")).mapToInt(s -> s.length()).sum();
		System.out.println("count:" + count);

		String[] words = Stream.of("C", "C++", "Java", "Scala", "Ruby").map(s -> s.toUpperCase()).sorted()
				.toArray(String[]::new);
		// 文字列連結用のメソッドが標準搭載された
		System.out.println("words:" + String.join(",", words));

		// Base64のエンコードとデコードが正式にサポートされた。
		Base64.Encoder encoder = Base64.getEncoder();
		String original = "user" + ":" + "pass";
		String encoded = encoder.encodeToString(original.getBytes(StandardCharsets.UTF_8));
		System.out.println("encoded:" + encoded);

		path = Paths.get("src/wordcount.txt");
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(System.out::println);
		} catch (IOException e) {
		}

		path = Paths.get("src/stream.txt");
		;
		try (Stream<String> lines = Files.lines(path)) {
			lines.filter(line -> !line.startsWith("#")).mapToInt(Integer::parseInt)
					.mapToObj(i -> (i % 3 == 0 ? "xxx" : String.valueOf(i))).forEach(System.out::println);
		} catch (IOException e) {
		}

		// ランダムな数字のリストを作成
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(5);
		list1.add(-77);
		list1.add(24);
		list1.add(3);
		list1.add(987);
		list1.add(56);
		list1.add(-9);
		list1.add(0);

		// 整列前のリストを出力
		System.out.println("ソート前:");
		list1.forEach(System.out::println);
		// for (int i : list1) {
		// System.out.println(i);
		// }

		// 降順に整列
		Collections.sort(list1, (arg1, arg2) -> arg2 - arg1);

		// 整列後のリストを出力
		System.out.println();
		System.out.println("ソート後:");
		// for (int i : list1) {
		// System.out.println(i);
		// }
		list1.forEach(System.out::println);

	}
}
