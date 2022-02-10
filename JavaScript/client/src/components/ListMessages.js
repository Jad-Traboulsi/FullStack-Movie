import React, { useEffect, useState } from "react";
import axios from "axios";
import dayjs from "dayjs";

const ListMessages = () => {
  const [messages, setMessages] = useState([]);
  var created = "";
  useEffect(() => {
    const getMessages = async () => {
      try {
        const res = await axios.get(
          process.env.REACT_APP_API_URL + "/messages/getAllMessagesOfUser",
          {
            withCredentials: true,
          }
        );
        setMessages(res.data);
      } catch (error) {
        console.log(error.response.data);
      }
    };

    getMessages();

    console.log(messages);
  }, []);
  return (
    <div className="pt-40 w-50 mx-auto">
      {messages.map((message) => (
        <div key={message._id} className="card mb-10 relative">
          <div className="card-body">
            <p>{message.msg}</p>
            <p className="txt-small txt-right">
              -- {dayjs(message.created_at).format("DD/MM/YY")}
            </p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ListMessages;
