import TeamWaiting from "./TeamWaiting";
import MoveSquareButton from "../../components/common/Button/MoveSquareButton";
// import profile1 from '../../img/test/profile1.png';
// import profile2 from '../../img/test/profile2.png';
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import { io } from "socket.io-client";
import StartGameButton from "../../components/common/Button/StartGameButton";

// const team1 = {
//   teamNo: 1,
//   player1: {
//     nickname: "DP조아",
//     id: "alias1031",
//     url: profile2,
//     isEmpty: false,
//   },
//   player2: {
//     nickname: "잠자는커비",
//     id: "sleepingkurby",
//     url: profile1,
//     isEmpty: false,
//   }
// };

// const team2 = {
//   teamNo: 2,
//   player1: {
//     nickname: "",
//     id: "",
//     url: ``,
//     isEmpty: true,
//   },
//   player2: {
//     nickname: "잠자는커비",
//     id: "sleepingkurby",
//     url: profile1,
//     isEmpty: false,
//   }
// }

export const BattleRoom = () => {
  const [members, setMembers] = useState([]);
  const navigate = useNavigate();
  const params = useParams();
  const roomToken = params.roomToken;
  const socket = io('http://localhost:3001', {reconnection:false});

  useEffect(() => {
    const userToken = JSON.parse(sessionStorage.getItem('access-token'));
    if(!userToken){
      alert('회원가입부터 해주세요!');
      navigate("/");
    }
    else{
      axios.get(`https://varabc.com:8080/member/getUserInfo`, {headers: {
        "access-token": userToken
      }}).then((res) => {
        console.log("방 참가자 정보 가져오기: " + res.data.userInfo);
        socket.emit('joinWaitingRoom', {
          roomToken: roomToken,
          member: res.data.userInfo 
        });
      }).catch((err) => {
        alert("서버에 문제가 생겼습니다! 나중에 다시 시도해주세요!");
        navigate("/");
      });
    }
  }, [navigate, roomToken, socket]);

  // 현재 방에 있는 참가자들 업데이트
  socket.on('updateWaitingRoom', ({members, userRoomIndex}) => {
    setMembers(...members);
    sessionStorage.setItem('userRoomIndex', JSON.stringify(userRoomIndex));
    console.log("참가자의 방 번호: " + userRoomIndex);
  });

  return (
    <>
      <div className="w-screen h-screen flex items-center bg-battleBlur bg-cover pl-20 pr-20">
        <div className="w-full flex justify-between items-end">
          <TeamWaiting player1={members[0]} player2={members[1]} teamNo={1} />
          <div>
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
              <StartGameButton roomToken={roomToken} members={members} />
            </div>
          </div>
          <TeamWaiting player1={members[2]} player2={members[3]} teamNo={2} />
        </div>
      </div>
    </>
  );
};