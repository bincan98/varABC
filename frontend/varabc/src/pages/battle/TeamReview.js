import React from "react";
import profile1 from '../../img/test/profile1.png';
import profile2 from '../../img/test/profile2.png';
import Review from "../../Review/Review";
import MoveRoundButton from "../../components/common/Button/MoveRoundButton";

const team = {
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

export const TeamReview = () => {
  return (
    <div className="flex justify-center relative">
      <div className="flex justify-center">
        <div className='w-[1400px] h-[750px] flex justify-center text-white bg-gray-700 m-8 rounded-[30px] shadow-lg z-50 '>
          <div className="absolute flex flex-col font-bold mt-[70px] mr-[300px]" >
            <h3 className="text-[70px]">짝의 코딩이 어땠나요?</h3>
            <p className="text-[40px]">짝을 위해 소중한 피드백을 남겨주세요!</p>
          </div>
          <div className="absolute flex items-center flex-col w-[760px] h-[500px] mt-[280px] mr-[300px]">
            <Review />
            <br />
            <textarea className="rounded-[30px]" cols={100} rows={10}></textarea>
          </div>
          <div className="absolute flex flex-col mt-[80px] ml-[700px]">
            <div className="absolute flex flex-col">
              <div>
                <img src={team.player2.url} alt="playerProfile" className="w-[300px] h-[300px] rounded-[16px] border-2" />
              </div>
              <div className="ml-[50px] mb-[50px]">
                <div className="text-white font-bold text-[40px]">{team.player2.nickname}</div>
                <div className="text-white font-bold text-[24px]">#{team.player2.id}</div>
              </div>
              <MoveRoundButton to={"/"} text={"회의 하기"} bgColor={"basic"} btnSize={"basic"} />
              <br />
              <MoveRoundButton to={"/"} text={"나가기"} bgColor={"red"} btnSize={"basic"} />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}