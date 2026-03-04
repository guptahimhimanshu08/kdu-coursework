import styles from "./Navbar.module.scss"

export const Navbar = () => {
    return (
        <nav className={styles.navbar}>
            <div className={styles.logo}>
                <h2><span>Clean</span>ly    </h2>
            </div>
                <div className={styles.phone}>
                    <h2>800-710-8420</h2>
                </div>
        </nav>
    )
}