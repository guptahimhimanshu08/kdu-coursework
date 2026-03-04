import { useDispatch, useSelector } from "react-redux"
import type { RootState } from "../../app/store"
import { setBathrooms, setBedrooms } from "../../features/bookingSlice"
import { useGetConfigQuery } from "../../services/configApi"
import styles from "./HomeDetails.module.scss"

export const HomeDetails = () => {
    console.log("Rendering HomeDetails component")
    const dispatch = useDispatch()
    const { data } = useGetConfigQuery()

    const selectedBathrooms = useSelector(
        (state: RootState) => state.bookingDraft.selection.bathrooms
    )

    const selectedBedrooms = useSelector(
        (state: RootState) => state.bookingDraft.selection.bedrooms
    )

    const handleBathroomSelect = (bathroomCount: number) => {
        const minBathrooms = data?.constraints?.minBathrooms ?? 0
        const maxBathrooms = data?.constraints?.maxBathrooms ?? 10
        if (bathroomCount >= minBathrooms && bathroomCount <= maxBathrooms) {
            dispatch(setBathrooms(bathroomCount))
        }
    }

    const handleBedroomSelect = (bedroomCount: number) => {
        const minBedrooms = data?.constraints?.minBedrooms ?? 0
        const maxBedrooms = data?.constraints?.maxBedrooms ?? 10
        if (bedroomCount >= minBedrooms && bedroomCount <= maxBedrooms) {
            dispatch(setBedrooms(bedroomCount))
        }
    }

    const iconMap: Record<string, string> = {
        bedrooms: "https://img.icons8.com/ios-filled/50/000000/bed.png",
        bathrooms: "https://img.icons8.com/ios-filled/50/000000/bath.png"
    };

   
    return (
        <div className={styles.homeDetailsContainer}>
            <h2>Tell us about your home</h2>
            <div className={styles.homeDetailsContent}>
                <div className={styles.homeDetailsItem}>
                    <div className={styles.homeDetailsItemHeader}>
                        <img src={iconMap.bedrooms} alt="bedroom" />
                        <span>Bedrooms</span>
                    </div>
                    <div className={styles.homeDetailsItemCounts}>
                        <button onClick={() => handleBedroomSelect(selectedBedrooms - 1)}>-</button>
                        <span>{selectedBedrooms}</span>
                        <button onClick={() => handleBedroomSelect(selectedBedrooms + 1)}>+</button>
                    </div>
                </div>
                <div className={styles.homeDetailsItem}>
                    <div className={styles.homeDetailsItemHeader}>
                        <img src={iconMap.bathrooms} alt="bathroom" />
                        <span>Bathrooms</span>
                    </div>
                    <div className={styles.homeDetailsItemCounts}>
                        <button onClick={() => handleBathroomSelect(selectedBathrooms - 1)}>-</button>
                        <span>{selectedBathrooms}</span>
                        <button onClick={() => handleBathroomSelect(selectedBathrooms + 1)}>+</button>
                    </div>
                </div>
            </div>

        </div>
    )
}