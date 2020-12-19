package com.posts.api.startup;

import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class CreateMocks {

	private final PostRepository postRepository;

	@PostConstruct
	public void setUp() {
		if (postRepository.count() > 0) {
			return;
		}

		postRepository.save(
			Post.builder()
				.author("wikipedia")
				.title("Kubernetes")
				.text("Kubernetes (comumente estilizado como K8s) é um sistema de orquestração de contêineres " +
					"open-source que automatiza a implantação, o dimensionamento e a gestão de aplicações em " +
					"contêineres. Ele foi originalmente projetado pelo Google e agora é mantido pela " +
					"Cloud Native Computing Foundation. Ele funciona com uma variedade de ferramentas de " +
					"conteinerização, incluindo Docker.")
				.build()
		);

		postRepository.save(
			Post.builder()
				.author("redhat")
				.title("Apache Kafka")
				.text("O Apache Kafka é uma alternativa aos sistemas de mensageria corporativos tradicionais. " +
					"Inicialmente, ele era um sistema interno desenvolvido pela LinkedIn para processar " +
					"1,4 trilhão de mensagens por dia. Mas agora é uma solução de transmissão de dados open source " +
					"aplicável a variadas necessidades corporativas.")
				.createdAt(LocalDateTime.now().minusNanos(7263121318761L))
				.build()
		);
	}

}
