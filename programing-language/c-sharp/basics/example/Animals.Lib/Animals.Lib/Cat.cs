using BabyScoller.SDK;

namespace Animals.Lib
{
    public class Cat:IAnimals
    {
        public void Voice(int times)
        {
            for (int i = 0; i < times; i++)
            {
                Console.WriteLine("喵喵...");
            }
        }

    }
}