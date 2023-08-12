import { useEffect } from "react";
import AddPlayerButton from "./AddPlayerButton";
import PlayerProfile from "./PlayerProfile";


const TeamWaiting = ({player1, player2, teamNo}) => {
    useEffect(() => {
        console.log(`${teamNo} 참가자 1`);
        console.log(player1);
    }, [player1]);
    useEffect(() => {
        console.log(`${teamNo} 참가자 2`);
        console.log(player2);
    }, [player2]);
    return (
        <div className="flex flex-col bg-primaryDark w-[414px] h-[416px] rounded-[25px] p-5 justify-around items-center">
            <div className="text-white text-[48px] font-bold">Team {teamNo}</div>
            { (player1 && player1.memberNo) ? <PlayerProfile player={player1} /> : <AddPlayerButton /> }
            { (player2 && player2.memberNo) ? <PlayerProfile player={player2} /> : <AddPlayerButton /> }
        </div>
    );
};

export default TeamWaiting;