
WebRTC

<h3>일반적인 데이터 공유 방식</h3>
- 클라이언트 <=> 서버 <=> 클라이언트
- 서버가 클라이언트의 데이터를 저장해서 다른 클라이언트에 전달

<h3>WebRTC 데이터 공유 방식</h3>
- 클라이언트 <=> 클라이언트
- 서버는 데이터를 주고받지 않고 클라이언트간의 연결만 설정(시그널링 - signaling)

<h3>ICE의 연결 처리 방법</h3>
1. UDP를 통해 직접 P 연결 시도
2. UDP 실패 시 TCP 시도
3. NAT와 방화벽으로 인해 실패시 STUN 서버 사용
4. STUN 실패시 TURN 서버 사용(대신 TURN 서버는 비용이 많이 들어 비선호)


참고 자료
https://carrotweb.tistory.com/107
