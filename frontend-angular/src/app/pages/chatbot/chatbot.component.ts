import { Component } from '@angular/core';
import { AiService } from '../../services/ai.service';

interface Message {
  sender: 'user' | 'bot';
  text: string;
}

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent {

  question: string = '';

  messages: Message[] = [
    {
      sender: 'bot',
      text: 'Bonjour, je suis l’assistant IA de CrimeAI. Posez-moi une question.'
    }
  ];

  constructor(private aiService: AiService) {}

  sendMessage(): void {
    if (!this.question.trim()) {
      return;
    }

    const userQuestion = this.question;

    this.messages.push({
      sender: 'user',
      text: userQuestion
    });

    this.question = '';

    this.aiService.chat(userQuestion).subscribe({
      next: (response) => {
        this.messages.push({
          sender: 'bot',
          text: response.answer
        });
      },
      error: (err) => {
        console.error('Erreur chatbot', err);

        this.messages.push({
          sender: 'bot',
          text: 'Erreur de connexion avec le service IA.'
        });
      }
    });
  }
}
