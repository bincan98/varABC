import TeamWaiting from "./TeamWaiting";
import MoveSquareButton from "../../components/common/Button/MoveSquareButton";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import StartGameButton from "../../components/common/Button/StartGameButton";
import SelectButton from "../../components/common/Button/SelectButton";
import socket from "../../modules/socketInstance";

export const BattleRoom = () => {
  const [members, setMembers] = useState([]);
  const navigate = useNavigate();
  const params = useParams();
  const roomToken = params.roomToken;
  const [selectedSource, setSelectedSource] = useState('');
  const [selectedDifficulty, setSelectedDifficulty] = useState('');

  const handleSourceSelect = (event) => {
    setSelectedSource(event.target.value);
  };

  const handleDifficultySelect = (event) => {
    setSelectedDifficulty(event.target.value);
  };


  useEffect(() => {
    sessionStorage.setItem('isPractice', JSON.stringify(false));
    const userToken = localStorage.getItem('access-token');
    if (!userToken) {
      alert('회원가입부터 해주세요!');
      navigate("/");
    }
    else{
      axios.get(`https://varabc.com:8080/member/getUserInfo`, {headers: {
        "access-token": userToken
      }}).then((res) => {
        console.log("방 참가자 정보 가져오기: ");
        console.log(res.data.userInfo);
        socket.emit('joinWaitingRoom', {
          roomToken: roomToken,
          member: res.data.userInfo
        });
      }).catch((err) => {
        alert("서버에 문제가 생겼습니다! 나중에 다시 시도해주세요!" + err);
        navigate("/");
      });
    }
    // eslint-disable-next-line
  }, []);

  socket.on('getUserRoomIndex', ({ userRoomIndex }) => {
    sessionStorage.setItem('userRoomIndex', userRoomIndex);
  });

  // 현재 방에 있는 참가자들 업데이트
  socket.on('updateWaitingRoom', ({ currMembers }) => {
    console.log("참가자 정보가 갱신되었습니다!");
    setMembers([...currMembers]);
  });

  socket.on('logMessage', (message) => {
    // 로그 메시지를 받아서 화면에 표시
    console.log('express log:', message);
  });

  return (
    <>
      <div className="w-screen h-screen flex items-center bg-battleBlur bg-cover pl-20 pr-20">
        <div className="w-full flex justify-between items-end">
          <TeamWaiting player1={members[0]} player2={members[1]} teamNo={1} />
          <div>
            <SelectButton
              selectedSource={selectedSource}
              selectedDifficulty={selectedDifficulty}
              onSourceSelect={handleSourceSelect}
              onDifficultySelect={handleDifficultySelect}
            />
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
              <StartGameButton roomToken={roomToken} members={members} source={selectedSource} difficulty={selectedDifficulty} />
            </div>
          </div>
          <TeamWaiting player1={members[2]} player2={members[3]} teamNo={2} />
        </div>
      </div>
    </>
  );
};