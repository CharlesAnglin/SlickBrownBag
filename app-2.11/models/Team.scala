package models

//* We will set teamId to auto increment, this means that Slick doesn't care what value we pass in as it will replace it with it's own value. So for ease we can just give it any default value.
case class Team(teamName: String, fullCapacity: Boolean, teamId: Int = 0)
