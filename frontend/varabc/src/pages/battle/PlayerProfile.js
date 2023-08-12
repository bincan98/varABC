
const PlayerProfile = ({player}) => {
    return (
        <div className="flex justify-start items-center w-[370px] h-[120px]">
            <div>
                <img src={player.memberImage} alt="playerProfile" className="w-[120px] h-[120px] rounded-[16px] border-2" />
            </div>
            <div className="ml-5">
                <div className="text-white font-bold text-[40px]">{player.memberNickname}</div>
                <div className="text-white font-bold text-[24px]">#{player.memberNo}</div>
            </div>
        </div>
    );
};

export default PlayerProfile;