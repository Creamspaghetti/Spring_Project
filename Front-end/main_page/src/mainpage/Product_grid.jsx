import styles from "./Product_grid.module.css";

export default function Product_grid({ musical_title, musical_image, musical_start_date, musical_end_date }) {
  return (
      <div className={styles.product_grid}>
        <div className={styles.product_imgbox}>
          <img className={styles.product_image} src={musical_image} />
        </div>
        <div className={styles.product_miniInfo}>
          <span className={styles.product_miniInfo_title}>{musical_title}</span><br/>
          <span className={styles.product_miniInfo_date}>{musical_start_date}~{musical_end_date}</span>
        </div>
      </div>
  );
}
