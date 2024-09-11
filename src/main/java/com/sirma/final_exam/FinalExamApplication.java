package com.sirma.final_exam;

import com.sirma.final_exam.service.DataLoader;
import com.sirma.final_exam.service.PlayerPairService;
import com.sirma.final_exam.service.TeamServiceHandler;
import com.sirma.final_exam.service.PlayerServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalExamApplication implements CommandLineRunner {
	@Autowired
	private PlayerServiceHandler playerServiceHandler;
	@Autowired
	private TeamServiceHandler teamServiceHandler;
	@Autowired
	private DataLoader dataloader;
	@Autowired
	private PlayerPairService playerPairService;

	public static void main(String[] args) {
		SpringApplication.run(FinalExamApplication.class, args);



		/*Properties props = new Properties();
		try {
			props.load(Files.newInputStream(Path.of("championship.properties"),
					StandardOpenOption.READ));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		var dataSource = new MysqlDataSource();
		dataSource.setServerName(props.getProperty("serverName"));
		dataSource.setPort(Integer.parseInt(props.getProperty("port")));
		dataSource.setDatabaseName(props.getProperty("databaseName"));
*/
	}


	@Override
	public void run(String... args) throws Exception {
		String playersCsv = "C:\\Users\\efree\\Desktop\\Sirma-Exam\\final-exam\\src\\main\\resources\\csv\\players.csv";
		String teamsCsv = "C:\\Users\\efree\\Desktop\\Sirma-Exam\\final-exam\\src\\main\\resources\\csv\\teams.csv";
		String matchesCsv = "C:\\Users\\efree\\Desktop\\Sirma-Exam\\final-exam\\src\\main\\resources\\csv\\matches.csv";
		String recordsCsv = "C:\\Users\\efree\\Desktop\\Sirma-Exam\\final-exam\\src\\main\\resources\\csv\\records.csv";
		//teamServiceHandler.loadTeamsFromCsv("C:\\Users\\efree\\Desktop\\Sirma-Exam\\final-exam\\src\\main\\resources\\csv\\teams.csv");
	//dataloader.loadPlayersCsv(playersCsv);
	//dataloader.loadTeamsCsv(teamsCsv);
		//dataloader.loadMatchesCsv(matchesCsv);
		//dataloader.loadRecordsCsv(recordsCsv);
		playerPairService.findLongestPlayingPlayersDifferentTeamsSamePair();
				//.findLongestPlayingPlayersDifferentTeamsSameMatch1();
				//.findLongestPlayingPlayersDifferentTeamsSameMatch();

	//	playerServiceHandler.loadPlayersFromCsv("C:\\Users\\efree\\Desktop\\Sirma-Exam\\final-exam\\src\\main\\resources\\csv\\teams.csv");

	}
}
