import { useSelector } from "react-redux"
import { useGetConfigQuery } from "../../services/configApi"
import type { RootState } from "../../app/store"
import React, { useEffect, useState } from "react"
import { computePricing } from "../../utils/computePricing"
import styles from "./SummarySection.module.scss"

export const SummarySection = React.memo(() => {
    const { data, error, isLoading } = useGetConfigQuery()
    const [pricing, setPricing] = useState<number | null>(null)
    const bookingDraft = useSelector((state: RootState) => state.bookingDraft)
    const selectedOptions = bookingDraft.selection
    const userDetails = bookingDraft.customer

    const icons = {
        type: "fas fa-paintbrush",
        date: "fas fa-calendar-alt",
        duration: "fas fa-clock",
        repeats: "fas fa-sync-alt",
        location: "fas fa-map-marker-alt"
    }

    useEffect(() => {
        if (data && selectedOptions && selectedOptions.cleaningTypeId && selectedOptions.frequencyId) {
            const computedPricing = computePricing(data, bookingDraft)
            setPricing(computedPricing.total)
        }
    }, [selectedOptions, data, bookingDraft])

    const cleaningTypeName = data?.cleaningTypes.find(t => t.id === selectedOptions.cleaningTypeId)?.name || ''
    const frequencyName = data?.cleaningFrequency.find(f => f.id === selectedOptions.frequencyId)?.name || ''

    return (
        <div className={styles.summaryContainer}>
            <div className={styles.summaryHeader}>
                <h2>Booking Summary</h2>
            </div>
            {isLoading && <p>Loading summary...</p>}
            {error && <p>Failed to load summary.</p>}

            <div className={styles.summaryDetails}>
                <p>
                    <i className={icons.type} />
                    <span>{cleaningTypeName || "-"}</span>
                </p>
                <p>
                    <i className={icons.date} />
                    <span>{selectedOptions.date || "-"} {selectedOptions.startTime || "-"}</span>
                </p>
                <p>
                    <i className={icons.duration} />
                    <span>{selectedOptions.hours || 0} hours</span>
                </p>
                <p>
                    <i className={icons.repeats} />
                    <span>{frequencyName || "-"}</span>
                </p>
                <p>
                    <i className={icons.location} />
                    <span>{userDetails.address || "-"}</span>
                </p>
                <div className={styles.pricingContainer}>
                    <h3>Total Cost</h3>
                    <p>{pricing ? `$${pricing.toFixed(2)}` : "-"}</p>
                </div>
            </div>
        </div>
    )
})