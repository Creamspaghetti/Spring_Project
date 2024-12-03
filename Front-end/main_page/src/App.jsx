import { useEffect, useState } from "react";
import React from "react";
import axios from "axios";
import "./App.css";
import Product_grid from "./app_conponent/Product_grid";

function App() {
  const [musicals, setMusicals] = useState([]); // Musical 데이터 상태

  useEffect(() => {
    // Musical 데이터 API 호출
    axios
      .get("http://localhost:9090/api/musicals")
      .then((response) => {
        setMusicals(response.data); // Musical 데이터 상태 업데이트
      })
      .catch((error) => {
        console.error("There was an error fetching musicals!", error);
      });
  }, []);

  return (
    <div className="App">
      {/* Header */}
      <header className="header width-limit">
        <div className="header-innner">
          <div className="logo">
            <img className="logo-image" src="./src/img/Muut_logo_v2.png" />
          </div>

          <div className="auth-buttons">
            <button>로그인</button>
            <button>회원가입</button>
          </div>
        </div>
      </header>

      {/* Main */}
      <main className="main">
        <div className="main-div highlight-event">
          <div className="event-details">
            <h2>광화문 연가</h2>
            <p>음악으로 Play되는 우리의 추억</p>
            <p>2024.10.23 - 2025.01.12 전국</p>
          </div>
          <img
            src="https://image.toast.com/aaaaab/ticketlink/TKL_5/2024-%EA%B4%91%ED%99%94%EB%AC%B8%EC%97%B0%EA%B0%80-%EB%9F%B0%EC%B9%AD(1)(1).jpg"
            alt="Highlighted Event"
          />
        </div>
        <section className="main-section width-limit section1">
          <ul className="product-grid ">
            {musicals.map((musical, index) => (
              <li key={index}>
                <Product_grid 
                  title={musical.musical_title} 
                  imageUrl={musical.musical_image} 
                  StartDate={musical.musical_start_date}
                  EndDate={musical.musical_end_date}
                />
              </li>
            ))}
          </ul>
        </section>
      </main>

      {/* Footer */}
      <footer className="footer">
        <div className="footer-info">
          <p>회사 정보 | 개인정보 처리방침 | 고객센터</p>
          <p>연락처: 1234-5678</p>
        </div>
      </footer>
    </div>
  );
}

export default App;
