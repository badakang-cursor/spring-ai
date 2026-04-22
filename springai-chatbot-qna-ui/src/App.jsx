import { useEffect, useRef, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'
import ChatModel from './components/ChatModel';

function App() {
  const [messages,setMessages] = useState([]);
  const [question, setQuestion] = useState('');
  const [loading, setLoading] = useState(false);
  const chatEndRef = useRef(null);

  useEffect (()=>{
    chatEndRef.current?.scrollIntoView({ behavior: 'smooth'})
  },[messages])

  const sendMessage = async () =>{
    if(!question.trim()) return;

    const newMessages = [...messages, {sender:'user', text:question}]
    setMessages(newMessages);
    setQuestion('');
    setLoading(true)

    try {
      const response = await fetch(`http://localhost:8080/api/hotel/chat?question=${encodeURIComponent(question)}`);

      const reader = response.body.getReader();
      const decoder = new TextDecoder('utf-8');
      let botMessage = '';

      while (true) {
        const {value, done} = await reader.read();
        if(done) break;

        const chunk = decoder.decode(value, {stream:true});
        botMessage += chunk;

        setMessages(prev => [...newMessages, {sender:'bot', text:botMessage}]);
      }

    } catch (error) {
      console.log("Error",error)
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className='chat-container'>
      <div className='chat-box'>
        {messages.map((msg,index) =>(
          <ChatModel key={index} message={msg} />
        ))}
        <div ref={chatEndRef}></div>
      </div>

      <div className='input-container'>
        <input value={question}
                onChange={e => setQuestion(e.target.value)}
                onKeyDown={e => e.key === 'Enter' && sendMessage()}
                placeholder='호텔 직원에게 문의해 보세요....'
        />
        <button onClick={sendMessage} disabled={loading}>전송</button>
      </div>

    </div>
  )
}

export default App
