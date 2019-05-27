package servlets;

	public class SelectedPlayer {
			String playerName;
			float handicap;
			float avgDelta;
			float avgScore;
			protected SelectedPlayer (String aPlayerName, float aHandicap,  float aAvgScore, float aAvgDelta){
			 playerName = aPlayerName;
			 handicap   = aHandicap;
			 avgDelta   = aAvgDelta;
			 avgScore   = aAvgScore;
			}
			public String getName (){
				return playerName;
			}
			public float getHandicap (){
				return handicap;
			}
			public float getDelta (){
				return avgDelta;
			}
			public float getAvgScore(){
				return avgScore;
			}
	}



