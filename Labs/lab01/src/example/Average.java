package example;

/**
 * Average example
 * @author Aarthi
 */
public class Average {

        /**
         * Returns the average of an array of numbers
         * @param the array of integer numbers
         * @return the average of the numbers
         */
        public float average(int[] nums) {
            float result = 0;
            int sum = 0;

            if (nums.length > 0) {
                for (int x : nums) {
                    sum += x;
                }
                result = (float) sum / nums.length;
            }

            return result;
        }

        public static void main(String[] args) {
            Average averager = new Average();

            int[] nums = {2,4,6,8,10};

            System.out.println(averager.average(nums));
        }
}
