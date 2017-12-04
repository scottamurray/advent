; https://adventofcode.com/2017/day/3

(ns advent.three)

(defn- rectilinear-distance
  "Returns the rectilinear distance between points [x1 y1] and [x2 y2]."
  [x1 y1 x2 y2]
  (+ (Math/abs (- x1 x2))
     (Math/abs (- y1 y2))))

(defn- distance-from-origin
  "Returns the rectilinear distance between the origin [0 0] and the given
  coordinates vector [x y]."
  [coordinates]
  (rectilinear-distance 0 0 (first coordinates) (last coordinates)))

(defn- square-root-rounded-up
  "Returns the square root of n, rounded up to the nearest integer."
  [n]
  (-> n
      (Math/sqrt)
      (Math/ceil)
      int))

(defn- nearest-greater-odd-square-root
  "Returns the nearest odd square root greater than the square root of the
  given value. Used to determine the width of the spiral graph layer that
  contains the given value."
  [value]
  (let [sqrt (square-root-rounded-up value)]
    (if (even? sqrt)
      (+ sqrt 1)
      sqrt)))

(defn- spiral-graph-layer-width
  "Returns the width of the spiral graph layer that contains the given value."
  [value]
  (nearest-greater-odd-square-root value))

(defn- value-in-bottom-row?
  "Returns true if the given value is in the bottom row of
  a spiral graph layer."
  [value max-spiral-value spiral-side-width]
  (>= max-spiral-value
      value
      (- max-spiral-value spiral-side-width)))

(defn- value-in-left-row?
  "Returns true if the given value is in the left-hand row of
  a spiral graph layer."
  [value max-spiral-value spiral-side-width]
  (>= (- max-spiral-value spiral-side-width)
      value
      (- max-spiral-value (* spiral-side-width 2) 1)))

(defn- value-in-top-row?
  "Returns true if the given value is in the top row of
  a spiral graph layer."
  [value max-spiral-value spiral-side-width]
  (>= (- max-spiral-value (* spiral-side-width 2) 1)
      value
      (- max-spiral-value (* spiral-side-width 3) 2)))

(defn- value-in-right-row?
  "Returns true if the given value is in the right-hand row of
  a spiral graph layer."
  [value max-spiral-value spiral-side-width]
  (>= (- max-spiral-value (* spiral-side-width 3) 2)
      value
      (- max-spiral-value (* spiral-side-width 4) 3)))

(defn- bottom-row-coordinates
  "Returns the coordinates of a value in the bottom row of a spiral graph
  layer with the given coordinate boundary, max value, and side width."
  [value coordinate-boundary max-spiral-value spiral-side-width]
  [(- coordinate-boundary
      (- max-spiral-value value))
   coordinate-boundary])

(defn- left-row-coordinates
  "Returns the coordinates of a value in the left row of a spiral graph
  layer with the given coordinate boundary, max value, and side width.
  There is a 1 cell offset to account for the previous sides' corners."
  [value coordinate-boundary max-spiral-value spiral-side-width]
  [(- coordinate-boundary)
   (- coordinate-boundary
      (- max-spiral-value
         spiral-side-width
         value
         1))])

(defn- top-row-coordinates
  "Returns the coordinates of a value in the top row of a spiral graph
  layer with the given coordinate boundary, max value, and side width.
  There is a 2 cell offset to account for the previous sides' corners."
  [value coordinate-boundary max-spiral-value spiral-side-width]
  [(+ (- coordinate-boundary)
      (- max-spiral-value
         (* spiral-side-width 2)
         value
         2))
   (- coordinate-boundary)])

(defn- right-row-coordinates
  "Returns the coordinates of a value in the right-hand row of a spiral graph
  layer with the given coordinate boundary, max value, and side width.
  There is a 3 cell offset to account for the previous sides' corners."
  [value coordinate-boundary max-spiral-value spiral-side-width]
  [coordinate-boundary
   (+ (- coordinate-boundary)
      (- max-spiral-value
         (* spiral-side-width 3)
         value
         3))])

(defn- side
  "Returns a keyword indicating on which side a value lies in a spiral graph
  layer with the given max value and side width."
  [value max-spiral-value spiral-side-width]
  (cond (value-in-bottom-row? value max-spiral-value spiral-side-width) :bottom
        (value-in-left-row? value max-spiral-value spiral-side-width) :left
        (value-in-top-row? value max-spiral-value spiral-side-width) :top
        (value-in-right-row? value max-spiral-value spiral-side-width) :right))

(defn- coordinates
  "Returns the X, Y coordinates of a given value on one side of a spiral graph
  layer with the given coordinate boundary, max value, and side width."
  [side value coordinate-boundary max-spiral-value spiral-side-width]
  (case side
    :bottom (bottom-row-coordinates value coordinate-boundary max-spiral-value spiral-side-width)
    :left (left-row-coordinates value coordinate-boundary max-spiral-value spiral-side-width)
    :top (top-row-coordinates value coordinate-boundary max-spiral-value spiral-side-width)
    :right (right-row-coordinates value coordinate-boundary max-spiral-value spiral-side-width)))

(defn distance-from-access-port
  "Returns the distance of the given memory value from the access port."
  [value]
  (let [side-width (spiral-graph-layer-width value)
        max-value (* side-width side-width)
        boundary (quot (- side-width 1) 2)]
    (-> value
        (side max-value side-width)
        (coordinates value boundary max-value side-width)
        (distance-from-origin))))

(distance-from-access-port 368078) ; => 371
