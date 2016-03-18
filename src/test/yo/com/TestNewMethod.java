package test.yo.com;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestNewMethod {

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
		// File.lines
		long start = System.currentTimeMillis();
		Files.lines(path).flatMap(line -> Arrays.stream(line.split("( |\\.|,)+")))// Map
				.collect(Collectors.groupingBy(word -> word, Collectors.counting()))// Reduce
				.forEach((k, v) -> System.out.println(k + ":" + v));// Output
		System.out.println("並行処理なし処理時間：" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		Files.lines(path).flatMap(line -> Arrays.stream(line.split("( |\\.|,)+")))// Map
				.parallel()// Concurrent
				.collect(Collectors.groupingBy(word -> word, Collectors.counting()))// Reduce
				.forEach((k, v) -> System.out.println(k + ":" + v));// Output
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

	}
}
