import styles from "./Button.module.scss";

interface ButtonProps {
    onClick: () => void;
}

export const Button = ({ onClick }: ButtonProps) => {

  return (
    <button className={styles.button} onClick={onClick}>
        Add User
    </button>
  );
};
