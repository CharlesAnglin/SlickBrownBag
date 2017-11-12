package models

//If the id is set to auto inc it doesn't matter what value you put in - it will handel it for you. So we just set it to 0 by default and never pass it in.
case class Team(teamName: String, fullCapacity: Boolean, teamId: Int = 0)
