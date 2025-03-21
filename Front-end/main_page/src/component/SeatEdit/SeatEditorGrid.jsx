import { useContext } from "react";
import { Seat } from "./seatContext";

export default function SeatEditorGrid({children, isGrade=true}) {
    const { handleRowSelect, seatData, selectAll, handleClearSelect, save_seats, selectedSeats, seats } = useContext(Seat);
  return (
    <>
      <div className="stage-position">
        <h1 id="stage">STAGE</h1>
      </div>
      <div className="drag-area">
        {children}
        <div className="selectRowButton">
          {Object.keys(seatData).map((row) => (
            <button
              key={row}
              className="button row-button"
              onClick={(e) => handleRowSelect(e, row)}
            >
              {row} 그룹선택
            </button>
          ))}
          {selectedSeats.length < 1 ? (
            <button className="button select-all-button" onClick={selectAll}>
              전체 선택
            </button>
          ) : (
            <button
              className="button select-clear-button"
              onClick={handleClearSelect}
            >
              전체 해제
            </button>
          )}
        </div>
        <div>
          <button className="save-button" type="button" onClick={()=>{
            if(!isGrade){
              alert("등급이 전부 저장되지 않았습니다. \n대상: " + seats.filter(seat=>seat.grade=="ALL").map(seat=>seat.id));
              return;
            }  
            save_seats();
          }}>
            저장
          </button>
        </div>
      </div>
    </>
  );
}
