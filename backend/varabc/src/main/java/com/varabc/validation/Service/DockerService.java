package com.varabc.validation.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import java.io.IOException;
import java.util.List;
import java.net.ServerSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class DockerService {
    private final DockerClient dockerClient;

    private int findAvailablePort(int startPort, int endPort) throws IOException {
        for (int port = startPort; port <= endPort; port++) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                // If the server socket is successfully created, the port is available
                return port;
            } catch (IOException e) {
                // Port is in use, try next port
            }
        }
        throw new IOException("No available port found in range " + startPort + "-" + endPort);
    }
    public String startPythonEvaluationContainer() {
        CreateContainerResponse container = dockerClient.createContainerCmd("pythonvalidation")
                .withCmd("isolatedPythonValidationRequestContainer", "-m", "http.server", "5005")  // 이 부분은 python으로 HTTP 서버를 시작하는 예제입니다. 실제 명령어는 원하는대로 수정해야 합니다.
                .withExposedPorts(new ExposedPort(5005))
                .exec();
        // 도커 컨테이너 시작
        dockerClient.startContainerCmd(container.getId()).exec();
        return container.getId();
    }
    public void stopPythonEvaluationContainer(String containerId) {
        // 도커 컨테이너 종료
        dockerClient.stopContainerCmd(containerId).exec();
        dockerClient.removeContainerCmd(containerId).exec();
    }
    public void restartContainer(String containerName) {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        for (Container container : containers) {
            for (String name : container.getNames()) {
                if (name.contains(containerName)) {
                    dockerClient.restartContainerCmd(container.getId()).exec();
                    return;
                }
            }
        }
    }
}