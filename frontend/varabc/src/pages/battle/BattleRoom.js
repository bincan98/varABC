import TeamWaiting from "./TeamWaiting";
import MoveSquareButton from "../../components/common/Button/MoveSquareButton";
import profile1 from '../../img/test/profile1.png';
import profile2 from '../../img/test/profile2.png';
import TextChat from "../../components/common/TextChat";

const team1 = {
  teamNo: 1,
  player1: {
    nickname: "DP조아",
    id: "alias1031",
    url: profile2,
    isEmpty: false,
  },
  player2: {
    nickname: "잠자는커비",
    id: "sleepingkurby",
    url: profile1,
    isEmpty: false,
  }
};

const team2 = {
  teamNo: 1,
  player1: {
    nickname: "",
    id: "",
    url: ``,
    isEmpty: true,
  },
  player2: {
    nickname: "잠자는커비",
    id: "sleepingkurby",
    url: profile1,
    isEmpty: false,
  }
}

export const BattleRoom = () => {
    return (
      <>
        <div className="w-screen h-screen flex items-center bg-battleBlur bg-cover pl-20 pr-20">
          <div className="w-full flex justify-between items-end">
            <TeamWaiting players={team1} />
            <div className="flex flex-col">
              <TextChat roomId="texttest" />
              <div className="flex w-[358px] justify-between items-end">
                <MoveSquareButton
                  text="친구 초대"
                  bgColor="basic"
                  btnSize="basic"
                />
                <MoveSquareButton
                  text="초대 URL"
                  bgColor="basic"
                  btnSize="basic"
                />
              </div>
              <div className="mt-4">
                <MoveSquareButton text="START!" bgColor="point" btnSize="big" />
              </div>
            </div>
            <TeamWaiting players={team2} />
          </div>
        </div>
      </>
    );
};